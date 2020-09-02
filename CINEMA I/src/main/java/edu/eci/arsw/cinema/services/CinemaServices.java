/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.services;


import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaFilter;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import edu.eci.arsw.cinema.persistence.CinemaFilterException;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristian
 */
@Service
public class CinemaServices {
    @Autowired
    @Qualifier("inMemoryCinemaPersistence")
    
    CinemaPersitence cps ;
    
    @Autowired
    @Qualifier("cinemaFilterGenero")
    
    CinemaFilter cfg;
    
    @Autowired
    @Qualifier("cinemaFilterDisponibilidad")
    
    CinemaFilter cfd;
    
    public void addNewCinema(Cinema c) throws CinemaPersistenceException{
        cps.saveCinema(c);
    }
    
    public Set<Cinema> getAllCinemas(){
        return cps.getAllCinemas();
    }
    
    /**
     * 
     * @param name cinema's name
     * @return the cinema of the given name created by the given author
     * @throws CinemaException
     */
    public Cinema getCinemaByName(String name){
    	Cinema resultado= null;
        try {
        	
            resultado = cps.getCinema(name);
        } catch (CinemaPersistenceException ex) {
            Logger.getLogger(CinemaServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultado;
    }
    
    
    public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException{
        cps.buyTicket(row, col, cinema, date, movieName);
    }
    
    public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) {
        return cps.getFunctionsbyCinemaAndDate(cinema, date);
    }
    public List<CinemaFunction> filtroGenero(String fecha, String cinema, String genero) throws CinemaFilterException {
    	List<CinemaFunction> funciones = getFunctionsbyCinemaAndDate(cinema, fecha);
		return cfg.filtros(funciones, genero);
	}
    public List<CinemaFunction> filtroDisponibilida(String name, String fecha, String asientos) throws CinemaFilterException{
    	List<CinemaFunction> funciones = getFunctionsbyCinemaAndDate(name, fecha);
    	return cfd.filtros(funciones, asientos);
    }

}
