package by.autobazar.services;

import by.autobazar.dao.CarDao;
import by.autobazar.dao.CommentDao;
import by.autobazar.dao.ICommentDao;
import by.autobazar.dao.exceptions.DaoException;
import by.autobazar.entity.Car;
import by.autobazar.entity.Comment;
import by.autobazar.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by Andrey
 * Date: 09.04.2017.
 * Time: 22:50
 */
@Service
public class CommentService extends BaseService<Comment> implements ICommentService {

    private static final Logger log = Logger.getLogger(CommentService.class);
    private ICarService carService;
    private IUserService userService;

    @Autowired
    private CommentService(ICommentDao commentDao, ICarService carService, IUserService userService) {
        super(commentDao);
        this.carService = carService;
        this.userService = userService;
    }

    public long createComment(String text, long carId, long userId) {
        log.info("Service createComment(): ");

        User user = ((UserService)userService).getUserById(userId);
        Car car = ((CarService)carService).getCarById(carId);

        Comment comment = new Comment(text, LocalDateTime.now());
        comment.setUser(user);
        comment.setCar(car);
        baseDao.saveOrUpdate(comment);
        return comment.getId();
    }

    public void deleteComment(Long id) {
        log.info("Service deleteComment(): ");
        Comment comment = null;
        comment = get(id);
        if (comment != null) {
            baseDao.delete(comment);
        }

    }
}
