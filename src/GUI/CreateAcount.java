package GUI;

import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.scene.chart.PieChart.Data;

public class CreateAcount {

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static boolean CreateAcountData(String User, String Name, String Age, String FavoriteGenres, String Pass) {

		String hostname = "172.18.30.36";
		int port = 8080;
		StringBuilder data = new StringBuilder();
		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("VerificarUsuarios");
			doc.appendChild(rootElement);

			// user elements
			Element user = doc.createElement("Usuario");
			rootElement.appendChild(user);

			user.setAttribute("User", User);
			user.setAttribute("Name", Name);
			user.setAttribute("Age", Age);
			user.setAttribute("FavoriteGenres", FavoriteGenres);
			user.setAttribute("Password", Pass);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("SignUpAcountData.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

		try (Socket socket = new Socket(hostname, port)) {

			String contenido = readFile("SignUpAcountData.xml", Charset.defaultCharset());
			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			writer.println(contenido);
			try {
				InputStream input = socket.getInputStream();
				InputStreamReader reader = new InputStreamReader(input);
				// System.out.println(output);
				int character;
				while ((character = reader.read()) != '\n') {
					data.append((char) character);
				}
				// socket.close();
				System.out.println(data);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (UnknownHostException ex) {

			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {

			System.out.println("I/O error: " + ex.getMessage());
		}
		if (data.toString().equals("true"))
			return true;
		else
			return false;
	}
}