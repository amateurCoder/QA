package edu.buffalo.qa.servlet;

import info.bliki.api.Connector;
import info.bliki.api.Page;
import info.bliki.api.User;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetImage
 */
@WebServlet("/GetImage")
public class GetImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 
			System.out.println("asda");
				// TODO Auto-generated method stub
			String pageName = "File:Sachin at Castrol Golden Spanner Awards (crop).jpg";
		        User user = new User("", "", "http://en.wikipedia.org/w/api.php");
		        Connector connector = new Connector();
		        user = connector.login(user);
		       String imgURL = null;
		       Image img;

		        System.out.println("PAGE-NAME: " + pageName);
		        // set image width thumb size to 200px
		        List<Page> pages = user.queryImageinfo(new String[] { pageName }, 200);

		        System.out.println("PAGES: " + pages.get(0).getTitle());

		        if (null != pages) {
		                System.out.println("PAGES: " + pages.size());
		                for (Page page : pages) {
		                	imgURL = page.getImageThumbUrl();
		                	if(null != imgURL){
		                		System.out.println("IMG-THUMB-URL: " + imgURL);
		                        URL url = new URL(imgURL);
		                        BufferedImage originalImage = ImageIO.read(url);
		                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
		                        ImageIO.write(originalImage, "jpg", baos );
		                        byte[] imageInByte=baos.toByteArray();
		                        response.setContentType("image/jpeg");
		                        response.getOutputStream().write(imageInByte);
		                	}
		                
		                }

		        } 
	}

}
