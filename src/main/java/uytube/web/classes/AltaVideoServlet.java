/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uytube.web.classes;


import uytube.web.wsclients.*;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author isaac
 */
@MultipartConfig
@WebServlet(name = "AltaVideoServlet", urlPatterns = {"/AltaVideoServlet"})
public class AltaVideoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        HttpSession s = request.getSession();
        DtUsuario user = (DtUsuario) s.getAttribute("usuario");
        String video = request.getParameter("nombreVideo").trim();
        String duracion = request.getParameter("duracion").trim();
        String url = request.getParameter("url").trim();
        String descripcion = request.getParameter("descripcion").trim();
        boolean estado = request.getParameter("estado").equals("privado");
        String categoria = request.getParameter("categorias").trim();

        String date = LocalDate.now().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = sdf.parse(date);

        DtCanal canal = user.getCanal();
        DtVideo v = new DtVideo();
        v.setNombre(video);
        v.setCanal(canal);

        GregorianCalendar calendario = new GregorianCalendar();
        calendario.setTime(fecha);
        XMLGregorianCalendar xmlCalendario = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendario);
        v.setFechaPublicacion(xmlCalendario);
        v.setUrl(url);
        v.setDescripcion(descripcion);
        v.setCategoria(categoria);
        v.setDuracion(duracion);
        v.setPrivado(estado);

        ControladorCanalService f = new ControladorCanalService();
        IControladorCanal controladorCanal = f.getControladorCanalPort();
        controladorCanal.registrarVideo(v);
        response.sendRedirect("index.jsp");
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try{
            processRequest(request, response);
        }
        catch (Exception e){
            System.out.println("Error");
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            processRequest(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}