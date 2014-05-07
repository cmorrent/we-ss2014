import model.Users;
import org.junit.Test;
import play.db.jpa.JPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

/**
 * Created by willi on 5/7/14.
 */
public class PersistenceTest {

    @Test
    public void persistUsersBeansAndList_shouldReturnCorrectUsers(){
        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {

                EntityManagerFactory emf = javax.persistence.Persistence
                        .createEntityManagerFactory("defaultPersistenceUnit");
                EntityManager em = emf.createEntityManager();
                JPA.bindForCurrentThread(em);

                Users user1 = new Users();
                user1.setName("Sepp");
                user1.setFirstname("Josef");
                JPA.em().persist(user1);

                Users user2 = new Users();
                user2.setName("Franz");
                JPA.em().persist(user1);

                Users userResult = (Users)JPA.em().createNamedQuery("Users.findByUsername")
                        .setParameter("name", "Sepp")
                        .getSingleResult();

                assertEquals(userResult.getFirstname(), "Josef");

            }
        });
    }
}
