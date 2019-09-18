package app02a.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app02a.domain.Product;
import app02a.form.ProductForm;

public class ControllerServlet extends HttpServlet{

    private static final long serialVersionUID=1597L;

    @Override
    public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException{
        process(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {
        process(request, response);
    }
    private void process(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException {
        String uri = request.getRequestURI();
        int lastIndex = uri.lastIndexOf("/");
        String action = uri.substring(lastIndex + 1);
        if (action.equals("product_intput.action")) {
            //no acton to do
        } else if (action.equals("product_save.action")) {
            ProductForm productForm = new ProductForm();
            productForm.setName(request.getParameter("name"));
            productForm.setDescription(request.getParameter("description"));
            productForm.setPrice(request.getParameter("price"));
            //System.out.println(request.getParameter("name"));
            Product product = new Product();
            product.setName(productForm.getName());
            product.setDescription(productForm.getDescription());
            try {
                product.setPrice(Float.parseFloat(productForm.getPrice()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            request.setAttribute("product", product);
        }
        //forward to a view
        String dispatchUrl=null;
        if(action.equals("product_input.action")){
            dispatchUrl="/WEB-INF/jsp/ProductForm.jsp";
        }else if(action.equals("product_save.action")){
            dispatchUrl="/WEB-INF/jsp/ProductDetails.jsp";
        }
        if(dispatchUrl!=null){
            RequestDispatcher rd=request.getRequestDispatcher(dispatchUrl);
            rd.forward(request,response);
        }
    }
}