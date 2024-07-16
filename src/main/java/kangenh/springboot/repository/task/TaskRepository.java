package kangenh.springboot.repository.task;

import kangenh.springboot.service.task.TaskEntity;
import kangenh.springboot.service.task.TaskSearchEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskRepository {
    @Select("""
            <script>
            SELECT id, summary, description, status
            FROM TASKS
            <where>
                <if test='condition.summary != null and !condition.summary.isBlank()'>
                    WHERE summary LIKE CONCAT('%', #{condition.summary}, '%')    
                </if>
            <if test='condition.status != null and !condition.status.isEmpty()'>
            AND status in(
                <foreach item='item' index='index' collection='condition.status' separator=','>
                        #{item}
                </foreach>
            )           
            </if>
            </where>
            </script>
            """)
    List<TaskEntity> select(@Param("condition") TaskSearchEntity condition);

    @Select("Select * from TASKS where id = #{taskId}")
    Optional<TaskEntity> selectById(@Param("taskId")Long taskId);

    @Insert("""
            insert into TASKS(summary, description, status) 
            values (#{task.summary}, #{task.description},#{task.status}  )
    """)
    void insert(@Param("task") TaskEntity taskEntity);

    @Update(
    """
        UPDATE tasks
        SET
            summary = #{task.summary},
            description = #{task.description},
            status = #{task.status}
        where id = #{task.id}
    """)
    void update(@Param("task") TaskEntity entity);

    @Delete("DELETE FROM tasks where id = #{taskId}")
    void delete(@Param("taskId") long id);

}
