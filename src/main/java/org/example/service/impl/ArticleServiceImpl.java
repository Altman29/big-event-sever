package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.mapper.ArticleMapper;
import org.example.pojo.Article;
import org.example.pojo.PageBean;
import org.example.service.ArticleService;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        //补充属性值
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //开启分页查询 PageHelper 需要导入pom
        PageHelper.startPage(pageNum, pageSize);

        //通过ThreadLocal获取用户id userId
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");

        //3.调用mapper 条件
        List<Article> as = articleMapper.list(userId, categoryId, state);
        //Page中提供了方法，可以获取PageHelper分页查询后 得到的总记录数和当前页数据
        Page<Article> p = (Page<Article>) as;


        //把数据填充到PageBean对象中
        //创建PageBean对象
        PageBean<Article> pb = new PageBean<>();
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
