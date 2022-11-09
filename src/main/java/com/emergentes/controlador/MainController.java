
package com.emergentes.controlador;

import com.emergentes.modelo.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verificar si la colección de personas ya existe en el obj session
        HttpSession ses = request.getSession();
        int id,pos;
        
        if (ses.getAttribute("listaprod") == null){
            // Crear la coleccion y almacenarla en el objeto session
            ArrayList<Producto> listaux = new ArrayList<Producto>();
            // Colocar listaux como atributo de session
            ses.setAttribute("listaprod", listaux);
        }
        
     ArrayList<Producto> lista = (ArrayList<Producto>)ses.getAttribute("listaprod");
        
        String op =  request.getParameter("op");
        String opcion = (op != null) ? op : "view";
        
        Producto objproducto = new Producto();
        
        switch (opcion){
            case "nuevo":
             request.setAttribute("miProducto", objproducto);
             request.getRequestDispatcher("editar.jsp").forward(request, response);
             break;
            case "editar":
             id = Integer.parseInt(request.getParameter("id"));
             pos = buscarIndice(request,id);
             objproducto = lista.get(pos);
             request.setAttribute("miProducto", objproducto);
             request.getRequestDispatcher("editar.jsp").forward(request, response);
             break;
            case "eliminar":
             id = Integer.parseInt(request.getParameter("id"));
             pos = buscarIndice(request,id);
             lista.remove(pos);
             response.sendRedirect("index.jsp");
             break;
            case "view":
                
             response.sendRedirect("index.jsp");
             break;
        }
        

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    HttpSession ses = request.getSession();
    ArrayList<Producto> lista = (ArrayList<Producto>) ses.getAttribute("listaprod");
    Producto objproducto = new Producto();
    int idt; 
        
      objproducto.setId(Integer.parseInt(request.getParameter("id")));
      objproducto.setDescripcion(request.getParameter("descripcion"));  
      objproducto.setCantidad(Integer.parseInt(request.getParameter("cantidad")));      
      objproducto.setPrecio(Double.parseDouble(request.getParameter("precio")));
      objproducto.setCategoria(request.getParameter("categoria"));   
        idt = objproducto.getId();
        if (idt == 0){
            objproducto.setId(ultimoID(request));
            lista.add(objproducto);
        }
        else{
            lista.set(buscarIndice(request,idt), objproducto);
        }
        response.sendRedirect("index.jsp");
    }
    
    private int ultimoID(HttpServletRequest request){
    HttpSession ses = request.getSession();
    ArrayList<Producto> lista = (ArrayList<Producto>)ses.getAttribute("listaprod");
        
        int idaux = 0;
        for (Producto item:lista){
            idaux = item.getId();
        }
        return idaux + 1;
    }
    
    private int buscarIndice(HttpServletRequest request, int id)
    {
     HttpSession ses = request.getSession();
     ArrayList<Producto> lista = (ArrayList<Producto>)ses.getAttribute("listaprod");
        
        int i = 0;
        if (lista.size()>0){
            while (i < lista.size()){
                if (lista.get(i).getId() == id ){
                    break;
                }
                else{
                    i++;
                }
            }
        }
        return i;
    }

}























