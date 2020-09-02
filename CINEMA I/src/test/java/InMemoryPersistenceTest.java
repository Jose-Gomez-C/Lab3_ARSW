
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import edu.eci.arsw.cinema.services.CinemaServices;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristian
 */
public class InMemoryPersistenceTest {
	private ApplicationContext classPaht;
	private CinemaServices cinemaServices;
	@Before
	public void setUp() {
		classPaht = new ClassPathXmlApplicationContext("applicationContext.xml");
		cinemaServices = classPaht.getBean(CinemaServices.class);
	}

	@Test
	public void saveNewAndLoadTest() throws CinemaPersistenceException{
		InMemoryCinemaPersistence ipct=new InMemoryCinemaPersistence();

		String functionDate = "2018-12-18 15:30";
		List<CinemaFunction> functions= new ArrayList<>();
		CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
		CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
		functions.add(funct1);
		functions.add(funct2);
		Cinema c=new Cinema("Movies Bogotá",functions);
		ipct.saveCinema(c);

		assertNotNull("Loading a previously stored cinema returned null.",ipct.getCinema(c.getName()));
		assertEquals("Loading a previously stored cinema returned a different cinema.",ipct.getCinema(c.getName()), c);
	}


	@Test
	public void saveExistingCinemaTest() {
		InMemoryCinemaPersistence ipct=new InMemoryCinemaPersistence();

		String functionDate = "2018-12-18 15:30";
		List<CinemaFunction> functions= new ArrayList<>();
		CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
		CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
		functions.add(funct1);
		functions.add(funct2);
		Cinema c=new Cinema("Movies Bogotá",functions);

		try {
			ipct.saveCinema(c);
		} catch (CinemaPersistenceException ex) {
			fail("Cinema persistence failed inserting the first cinema.");
		}

		List<CinemaFunction> functions2= new ArrayList<>();
		CinemaFunction funct12 = new CinemaFunction(new Movie("SuperHeroes Movie 3","Action"),functionDate);
		CinemaFunction funct22 = new CinemaFunction(new Movie("The Night 3","Horror"),functionDate);
		functions.add(funct12);
		functions.add(funct22);
		Cinema c2=new Cinema("Movies Bogotá",functions2);
		try{
			ipct.saveCinema(c2);
			fail("An exception was expected after saving a second cinema with the same name");
		}
		catch (CinemaPersistenceException ex){

		}


	}

	@Test
	public void shouldBeAbletoReturnNameCinema(){

		Cinema cine = cinemaServices.getCinemaByName("cinemaX");
		assertEquals(cine.getName(),"cinemaX");

	}
	@Test
	public void shouldBeAbleToBuyTicket(){
		Cinema cine = cinemaServices.getCinemaByName("cinemaX");

		int beforeSeats = contarPuestos(cine) ;
		try {
			cinemaServices.buyTicket(1, 1, "cinemaX", "2018-12-18 15:30", "The Night");
		} catch (CinemaException ex) {
			Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
		}
		int afterSeats = contarPuestos(cine);
		assertEquals(beforeSeats-1 , afterSeats);
	}

	@Test
	public void shouldBeAbletogetFunctionsbyCinemaAndDate(){
		List<String> correcta= new ArrayList<String>();
		correcta.add("SuperHeroes Movie");
		correcta.add("The Night");
		List<CinemaFunction> cine = cinemaServices.getFunctionsbyCinemaAndDate("cinemaX", "2018-12-18 15:30");
		List<String> calculada = getNameMovie(cine);

		assertEquals(correcta, calculada);



    }
    @Test
    public void shouldBeAbleToBuyAll() {
            Cinema cine = cinemaServices.getCinemaByName("cinemaX");

            int beforeSeats = contarPuestos(cine);
            int j = 0;
            int k = 0;
            try {
                for (int i = 0; i < 84; i++) {
                    if (k % 12 == 0 && k != 0) {
                        j++;
                        k = 0;
                    }
                    cinemaServices.buyTicket(j, k, "cinemaX", "2018-12-18 15:30", "The Night");
                    k++;
                }
        } catch (CinemaException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        int afterSeats = contarPuestos(cine);
        assertEquals(0, afterSeats);
        try {
            cinemaServices.buyTicket(6, 11, "cinemaX", "2018-12-18 15:30", "The Night");
        } catch (CinemaException e) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldBeAbleToNotBuyAll() {
        Cinema cine = cinemaServices.getCinemaByName("cinemaX");
        int beforeSeats = contarPuestos(cine);
        int afterSeats = contarPuestos(cine);

        assertEquals(beforeSeats, afterSeats);
    }

    private List<String> getNameMovie(List<CinemaFunction> funciones) {
        List<String> movies = new ArrayList<String>();
        for (CinemaFunction i : funciones) {
            movies.add(i.getMovie().getName());
        }
        return movies;
    }

    private int contarPuestos(Cinema cine) {
        int puestos = 0;
        for (CinemaFunction i : cine.getFunctions()) {
            if (i.getMovie().getName().equals("The Night") && i.getDate().equals("2018-12-18 15:30")) {
                for (int j = 0; j < i.getSeats().size(); j++) {
                    for (int k = 0; k < i.getSeats().get(j).size(); k++) {
                        if (i.getSeats().get(j).get(k)) {
                            puestos += 1;
                        }
                    }
                }
            }
        }
        return puestos;
    }

}
