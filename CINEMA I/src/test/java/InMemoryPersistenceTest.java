
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import edu.eci.arsw.cinema.services.CinemaServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
        ApplicationContext classPaht = new ClassPathXmlApplicationContext("applicationContext.xml");
        CinemaServices cinemaServices = classPaht.getBean(CinemaServices.class);
        Cinema cine = cinemaServices.getCinemaByName("cinemaX");
        assertEquals(cine.getName(),"cinemaX");
		
    }
    @Test
    public void shouldBeAbleToBuyTicket(){
        Map<String,Cinema> cinemas=new HashMap<>();
        ApplicationContext classPaht = new ClassPathXmlApplicationContext("applicationContext.xml");
        CinemaServices cinemaServices = classPaht.getBean(CinemaServices.class);
        Cinema cine = cinemaServices.getCinemaByName("cinemaX");
        
        /**List<CinemaFunction> functions = cinemaServices.getFunctionsbyCinemaAndDate("cinemaX","2018-12-18 15:30");
        CinemaFunction function = functions.get(0);**/
        int beforeSeats = 0;
        int afterSeats = 0;
        for (CinemaFunction i : cine.getFunctions()) {
            if (i.getMovie().getName().equals("The Night") && i.getDate().equals("2018-12-18 15:30")) {
                beforeSeats = i.getSeats().size();
            }
        }/**
        System.out.println(beforeSeats+"..");
        try {
            cinemaServices.buyTicket(1, 1, "cinemaX", "2018-12-18 15:30", "The Night");
        } catch (CinemaException ex) {
            Logger.getLogger(InMemoryPersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (CinemaFunction i : cine.getFunctions()) {
            if (i.getMovie().getName().equals("The Night") && i.getDate().equals("2018-12-18 15:30")) {
                System.out.println("Aca llego");
                for (int j= 0; j< i.getSeats().size();j++){
                    
                    if(i.getSeats().equals(true)){
                        afterSeats += 1;
                    }
                }
            }
        }
        System.out.println(afterSeats+"....");
        assertTrue(beforeSeats > afterSeats);
              **/  
    }
}
