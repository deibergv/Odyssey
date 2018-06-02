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

public class SongName {

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public static void Song(String name, String album, String año, String crude, String genero, String letra) {

		String hostname = "172.18.30.36";
		int port = 8080;

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("EnviarCancion");
			doc.appendChild(rootElement);

			// staff elements
			Element user = doc.createElement("Cancion");
			rootElement.appendChild(user);

			// set attribute to staff element
			// Attr attr = doc.createAttribute("id");
			// attr.setValue("1");
			// staff.setAttributeNode(attr);

			// shorten way
			user.setAttribute("nombre", name);
			user.setAttribute("ano", año);
			user.setAttribute("genero", genero);
			user.setAttribute("crudo", crude);
			user.setAttribute("album", album);
			user.setAttribute("letra", letra);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("Song.xml"));

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
			// File archivo = new File("/home/tony/CLionProjects/users.xml");
			// if(!archivo.exists()){
			// archivo.createNewFile();
			// }
			String contenido = readFile("Song.xml", Charset.defaultCharset());
			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			writer.println(contenido);
			try {
				InputStream input = socket.getInputStream();
				InputStreamReader reader = new InputStreamReader(input);
				// System.out.println(output);
				int character;
				StringBuilder data = new StringBuilder();
				System.out.println(data);
				while ((character = reader.read()) != '\n') {
					data.append((char) character);

				}
				socket.close();
				System.out.println(data);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (UnknownHostException ex) {

			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {

			System.out.println("I/O error: " + ex.getMessage());
		}
	}

}
