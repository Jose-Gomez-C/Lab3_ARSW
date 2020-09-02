/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author cristian
 */
@Component
@Qualifier("inMemoryCinemaPersistence")

public class InMemoryCinemaPersistence implements CinemaPersitence{

	private final Map<String,Cinema> cinemas=new HashMap<>();

	public InMemoryCinemaPersistence() {
		//load stub data
		String functionDate = "2018-12-18 15:30";
		List<CinemaFunction> functions= new ArrayList<>();
		CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie","Action"),functionDate);
		CinemaFunction funct2 = new CinemaFunction(new Movie("The Night","Horror"),functionDate);
		functions.add(funct1);
		functions.add(funct2);
		Cinema c=new Cinema("cinemaX",functions);
		cinemas.put("cinemaX", c);
	}    

	@Override
	public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException {
		Cinema cineElegido = cinemas.get(cinema);
		for (CinemaFunction i: cineElegido.getFunctions()) {
			if (i.getMovie().getName().equals(movieName) && i.getDate().equals(date)) {
				i.buyTicket(row, col);
			}
		}
	}

	@Override
	public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) {
		Cinema cineElegido = cinemas.get(cinema);
		List<CinemaFunction> funcionesFecha = new ArrayList<CinemaFunction>();
		for(CinemaFunction i : cineElegido.getFunctions()) {
			if(i.getDate().equals(date)) {
				funcionesFecha.add(i);
			}
		}
		return funcionesFecha;
	}

	@Override
	public void saveCinema(Cinema c) throws CinemaPersistenceException {
		if (cinemas.containsKey(c.getName())){
			throw new CinemaPersistenceException("The given cinema already exists: "+c.getName());
		}
		else{
			cinemas.put(c.getName(),c);
		}   
	}

	@Override
	public Cinema getCinema(String name) throws CinemaPersistenceException {
		return cinemas.get(name);
	}
	@Override
	public Set<Cinema> getAllCinemas(){
		Set<Cinema> todoCinemas = new HashSet<Cinema>(cinemas.values());
		return todoCinemas;
	}
}
