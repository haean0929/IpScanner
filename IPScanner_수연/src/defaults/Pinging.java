package defaults;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;

public class Pinging extends Thread {

	private Object[] msg;
	private String ip;
	private ImageIcon originIcon3 = new ImageIcon(System.getProperty("user.dir") + "\\src\\question-icon.png");
	private Image originImg3 = originIcon3.getImage();
	private Image changedImg3 = originImg3.getScaledInstance(12, 12, Image.SCALE_SMOOTH);
	ImageIcon questionIcon = new ImageIcon(changedImg3);
			
	private ImageIcon originIcon4 = new ImageIcon(System.getProperty("user.dir") + "\\src\\Blue-icon.jpg");
	private Image originImg4= originIcon4.getImage();
	private Image changedImg4 = originImg4.getScaledInstance(12, 12, Image.SCALE_SMOOTH);
	ImageIcon pingYIcon = new ImageIcon(changedImg4);
	
	private ImageIcon originIcon5 = new ImageIcon(System.getProperty("user.dir") + "\\src\\Red-icon.jpg");
	private Image originImg5 = originIcon5.getImage();
	private Image changedImg5 = originImg5.getScaledInstance(12, 12, Image.SCALE_SMOOTH);
	ImageIcon pingNIcon = new ImageIcon(changedImg5);
	
	public Pinging(String ip, Object[] msg) {
		this.ip = ip;
		this.msg = msg;
	}
	@Override
	public void run() {
		JLabel questionLabel = new JLabel(ip);
		JLabel pingYLabel = new JLabel(ip);
		JLabel pingNLabel = new JLabel(ip);
		questionLabel.setIcon(questionIcon);
		pingYLabel.setIcon(pingYIcon);
		pingNLabel.setIcon(pingNIcon);
		BufferedReader br = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("ping -a " + ip);
			msg[0] = questionLabel;
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String lineInput = null;
			
			
			while ((lineInput = br.readLine()) != null) {
				if (lineInput.indexOf("[") >= 0) {
					msg[3] = lineInput.substring(5, lineInput.indexOf("[") - 1);
				}
				if (lineInput.indexOf("ms") >= 0) {
					msg[1] = lineInput.substring(0,lineInput.indexOf("ms") + 2);
					msg[1] = ((String)msg[1]).substring(((String)msg[1]).lastIndexOf("=")+1);
					if(msg[1].toString().length() > 6) {
						msg[1] = ((String)msg[1]).substring(((String)msg[1]).lastIndexOf("<"));
					}
					msg[2] = lineInput.substring(lineInput.indexOf("TTL=")+4);
					msg[0] = pingYLabel;
					break;
				}
				
			}
			
			
			if (msg[1] == null) {
				msg[1] = "[n/a]";
				msg[0] = pingNLabel;
			}
			if (msg[2] == null) msg[2] = "[n/s]";
			if (msg[3] == null) msg[3] = "[n/s]";
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		
	}

}
