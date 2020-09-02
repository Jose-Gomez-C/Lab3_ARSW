
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaFilterException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.services.CinemaServices;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CinemaServiceTest {

    private ApplicationContext classPaht;
    private CinemaServices cinemaServices;

    @Before
    public void setUp() {
        classPaht = new ClassPathXmlApplicationContext("applicationContext.xml");
        cinemaServices = classPaht.getBean(CinemaServices.class);
    }

    @Test
    public void shouldBeAbleToAddCinema() {

        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
        CinemaFunction funct3 = new CinemaFunction(new Movie("The Night 3", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        functions.add(funct3);
        Cinema c = new Cinema("Movies Bogotá", functions);
        try {
            cinemaServices.addNewCinema(c);
        } catch (CinemaPersistenceException e) {
            fail("No pude agregar un nuevo cinema");
        }

        functionDate = "2018-12-18 15:30";
        functions = new ArrayList<>();
        funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
        funct3 = new CinemaFunction(new Movie("The Night 3", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        functions.add(funct3);
        Cinema d = new Cinema("Movies Medellin", functions);
        try {
            cinemaServices.addNewCinema(d);
        } catch (CinemaPersistenceException e) {
            fail("No pude agregar un nuevo cinema");
        }
        assertEquals("Movies Bogotá", cinemaServices.getCinemaByName("Movies Bogotá").getName());
        assertEquals("Movies Medellin", cinemaServices.getCinemaByName("Movies Medellin").getName());
    }

    @Test
    public void shouldBeAbleToGetAllCinemas() {
        Set<Cinema> todoCinemas = new HashSet<Cinema>();
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        functions.add(funct1);
        Cinema c = new Cinema("Movies Bogotá", functions);
        try {
            cinemaServices.addNewCinema(c);
            todoCinemas.add(c);
        } catch (CinemaPersistenceException e) {
            fail("No pude agregar un nuevo cinema");
        }
        
        functionDate = "2018-12-18 15:30";
        functions = new ArrayList<>();
        funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        functions.add(funct1);
        Cinema d = new Cinema("Movies Soacha", functions);
        try {
            cinemaServices.addNewCinema(d);
            todoCinemas.add(d);
        } catch (CinemaPersistenceException e) {
            fail("No pude agregar un nuevo cinema");
        }
        
        functionDate = "2018-12-18 15:30";
        functions = new ArrayList<>();
        funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
        functions.add(funct1);
        Cinema s = new Cinema("Movies Suba", functions);
        try {
            cinemaServices.addNewCinema(s);
            todoCinemas.add(s);
        } catch (CinemaPersistenceException e) {
            fail("No pude agregar un nuevo cinema");
        }
        

        assertEquals(todoCinemas.size(), cinemaServices.getAllCinemas().size() - 1);

    }
    @Test
    public void shouldBeAbletoGetCinemaByName(){
       
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("Carlo Magno", "Action"), functionDate);
        functions.add(funct1);
        Cinema c = new Cinema("Movies Bosa", functions);
        try {
            cinemaServices.addNewCinema(c);
            
        } catch (CinemaPersistenceException e) {
            fail("No pude agregar un nuevo cinema");
        }
        Cinema cine = cinemaServices.getCinemaByName("Movies Bosa");
        assertEquals(cine.getName(), "Movies Bosa");
    }
    
    
    
    @Test
    public void shouldBeAbleToBuyTicket() {
        Cinema cine = cinemaServices.getCinemaByName("cinemaX");

       int beforeSeats = contarPuestos(cine);
        try {
            
            cinemaServices.buyTicket(3,4, "cinemaX", "2018-12-18 15:30", "SuperHeroes Movie");
            cinemaServices.buyTicket(5,6 , "cinemaX", "2018-12-18 15:30", "SuperHeroes Movie");
            
            
        } catch (CinemaException ex) {
            Logger.getLogger(CinemaServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        int afterSeats = contarPuestos(cine);

        assertEquals(beforeSeats - 2, afterSeats);

    }

    @Test
    public void shouldBeAbleTogetFunctionsbyCinemaAndDate() {
        List<String> correcta = new ArrayList<String>();

        String functionDate = "2018-12-18 16:30";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperChampions", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("Half Pass", "Comedy"), functionDate);
        CinemaFunction funct3 = new CinemaFunction(new Movie("The Ring", "Horror"), functionDate);
        correcta.add("SuperChampions");
        correcta.add("Half Pass");
        correcta.add("The Ring");
        functions.add(funct1);
        functions.add(funct2);
        functions.add(funct3);
        Cinema c = new Cinema("Movies Pereira", functions);
        try {
            cinemaServices.addNewCinema(c);
        } catch (CinemaPersistenceException e) {
            fail("No pude agregar un nuevo cinema");
        }
        List<CinemaFunction> cine = cinemaServices.getFunctionsbyCinemaAndDate("Movies Pereira", "2018-12-18 16:30");
        List<String> calculada = getNameMovie(cine);

        assertEquals(correcta, calculada);

    }

    @Test
    public void shouldBeAbleToFilterMovieAvaible() {
        List<CinemaFunction> funcionesFiltradas = null;
        String functionDate = "2020-05-15 07:25";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperChampions", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("Half Pass", "Comedy"), functionDate);
        CinemaFunction funct3 = new CinemaFunction(new Movie("The Ring", "Horror"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        functions.add(funct3);
        Cinema c = new Cinema("Movies Pereira", functions);
        try {
            cinemaServices.addNewCinema(c);
        } catch (CinemaPersistenceException e) {
            fail("No pude agregar un nuevo cinema");
        }
        List<CinemaFunction> funcionesCorrectas = new ArrayList<CinemaFunction>();
        funcionesCorrectas.add(funct1);
        funcionesCorrectas.add(funct2);
        funcionesCorrectas.add(funct3);
        try {
            funcionesFiltradas = cinemaServices.filtroDisponibilida("Movies Pereira", "2020-05-15 07:25", "83");
        } catch (CinemaFilterException e) {
            fail("No se encontro la contidad requerida.");
        }
        assertEquals(funcionesFiltradas, funcionesCorrectas);
    }
    @Test
    public void shouldBeAbleToFilterMovieGender() {
        List<CinemaFunction> funcionesFiltradas = null;
        String functionDate = "2020-05-15 07:25";
        List<CinemaFunction> functions = new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperChampions", "Action"), functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("Half Pass", "Comedy"), functionDate);
        CinemaFunction funct3 = new CinemaFunction(new Movie("The Ring", "Horror"), functionDate);
        CinemaFunction funct4 = new CinemaFunction(new Movie("Half Pass 2", "Comedy"), functionDate);
        functions.add(funct1);
        functions.add(funct2);
        functions.add(funct3);
        functions.add(funct4);
        Cinema c = new Cinema("Movies Pereira", functions);
        try {
            cinemaServices.addNewCinema(c);
        } catch (CinemaPersistenceException e) {
            fail("No pude agregar un nuevo cinema");
        }
        List<CinemaFunction> funcionesCorrectas = new ArrayList<CinemaFunction>();
       
        funcionesCorrectas.add(funct2);
        funcionesCorrectas.add(funct4);
        try {
            funcionesFiltradas = cinemaServices.filtroGenero("2020-05-15 07:25","Movies Pereira", "Comedy");
        } catch (CinemaFilterException e) {
            fail("No se encontro la contidad requerida.");
        }
        assertEquals(funcionesFiltradas, funcionesCorrectas);
    }


    

    private int contarPuestos(Cinema cine) {
        int puestos = 0;
        for (CinemaFunction i : cine.getFunctions()) {
            if (i.getMovie().getName().equals("SuperHeroes Movie") && i.getDate().equals("2018-12-18 15:30")) {
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
    private List<String> getNameMovie(List<CinemaFunction> funciones) {
        List<String> movies = new ArrayList<String>();
        for (CinemaFunction i : funciones) {
            movies.add(i.getMovie().getName());
        }
        return movies;
    }
    

}
