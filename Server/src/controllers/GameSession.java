package controllers;

import Core.Game;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GameSession {
    private HttpSession httpSession;
    private Game game;
    private String backlog;
    
    public synchronized void setBacklog(String backlog) {
        this.backlog = backlog;
    }
    
    public String getBacklog() {
        return backlog;
    }
    
    public HttpSession getHttpSession() {
        return httpSession;
    }
    
    public Game getGame() {
        return game;
    }
    
    public GameSession(HttpSession httpSession, Game game) {
        this.httpSession = httpSession;
        this.game = game;
    }
}
