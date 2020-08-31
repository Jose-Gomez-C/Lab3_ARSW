import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import edu.eci.arsw.cinema.services.CinemaServices;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import static org.junit.Assert.assertEquals;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;
public class CinemaServicesTest {

	@Test
	public void shouldBeAbletoReturnNameCinema(){
		ApplicationContext classPaht = new ClassPathXmlApplicationContext("applicationContext.xml");
		CinemaServices cinemaServices = classPaht.getBean(CinemaServices.class);
		Cinema cine = cinemaServices.getCinemaByName("cinemaX");
		assertEquals(cine.getName(),"cinemaX");
		
	}
}
