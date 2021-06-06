package org.example.test.mapper;

import org.example.annotations.Delete;
import org.example.annotations.Insert;
import org.example.annotations.Select;
import org.example.annotations.Update;
import org.example.test.entity.Blog;

import java.util.List;

/**
 * @author zhou1
 */
public interface BlogMapper {

    /**
     * xml 查询
     */
    Blog selectBlog(Integer id);

    /**
     * xml 查询所有
     */
    List<Blog> selectAllBlog();

    /**
     * xml 新增
     */
    int insertBlog(String title, String content);

    /**
     * xml 更新
     */
    int updateBlog(String title, Integer id);

    /**
     * xml 删除
     */
    int deleteBlog(Integer id);


    /****************************** 注解方式实现 ****************************************/

    /**
     * 注解方式查询
     */
    @Select("select * from blog where id = #{id}")
    Blog selectBlogById(Integer id);

    /**
     * 注解方式查询
     */
    @Select("select * from blog where title = #{title} and content = #{content}")
    List<Blog> selectBlogByTitleAndContent(String title, String content);

    @Insert("insert into blog(title,content) values(#{title},#{content})")
    int insertAnnoBlog(String title, String content);

    @Update("update blog set title = #{title} where id = #{id}")
    int updateAnnoBlog(String title, Integer id);

    @Delete("delete from blog where id = #{id}")
    int deleteAnnoBlog(Integer id);

}
