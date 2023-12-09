package com.example.demo.service;

import com.example.demo.entity.News;
import com.example.demo.entity.Post;
import com.example.demo.mapper.NewsMapper;
import com.example.demo.repository.NewsRepository;
import com.example.demo.utils.JwtTokenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final NewsMapper newsMapper;

    public News createNews(News news, String token) {
        news.setUserId(userService.findByUserName(jwtTokenUtils.getUsername(token))
                .orElseThrow(RuntimeException::new));
        news.setCreatedAt(LocalDateTime.now());
        return newsRepository.save(news);
    }

    public List<News> getAll() {
        return newsRepository.findAll();
    }

    @Transactional
    public News update(News news, long id,String token) {
        var newsToUpdate = newsRepository.findById(id).get();
        newsMapper.update(newsToUpdate, news);
        newsToUpdate.setCreatedAt(LocalDateTime.now());
        newsToUpdate.setUserId(userService.findByUserName(jwtTokenUtils.getUsername(token))
                .orElseThrow(RuntimeException::new));
        return newsRepository.save(newsToUpdate);
    }

    @Transactional
    public void delete(long id) {
        newsRepository.deleteById(id);
    }

    public News getNews(Long newsId) {
        return newsRepository.findById(newsId).orElseThrow(RuntimeException::new);
    }
}
