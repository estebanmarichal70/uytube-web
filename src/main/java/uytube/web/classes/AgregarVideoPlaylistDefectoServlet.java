/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uytube.web.classes;

import DataTypes.DtListaParticulares;
import DataTypes.DtUsuario;
import fabrica.Fabrica;
import interfaces.IControladorCanal;
import interfaces.IControladorUsuario;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@MultipartConfig
@WebServlet(name = "AgregarVideoPlaylistDefectoServlet", urlPatterns = {"/AgregarVideoPlaylistDefectoServlet"})
public class AgregarVideoPlaylistDefectoServlet extends HttpServlet {

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
            throws IOException {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        HttpSession s = request.getSession();

        DtUsuario user = null;
        if (s.getAttribute("usuario") != null) {
            user = (DtUsuario) s.getAttribute("usuario");
        }

        String nomUser = user.getNickname();
        String nomLista = request.getParameter("nomPlaylist");
        String nomVideo = request.getParameter("nomVideo");
        String nomCanal = request.getParameter("nomCanal");

        Fabrica fabrica = Fabrica.getInstance();
        IControladorCanal controladorCanal = fabrica.getControladorCanal();
        IControladorUsuario controladorUsuario = fabrica.getControladorUsuario();

        DtUsuario destino = controladorUsuario.buscarUsuarioCanal(nomCanal);
        String userDestino = destino.getNickname();

        System.out.println(userDestino);
        System.out.println(nomVideo);
        System.out.println(nomLista);
        System.out.println(nomUser);

        try{
            controladorCanal.agregarVideoLista(nomVideo,nomLista,nomUser,userDestino,false);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
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
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);

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