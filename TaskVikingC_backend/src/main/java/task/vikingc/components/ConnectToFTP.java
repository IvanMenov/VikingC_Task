package task.vikingc.components;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConnectToFTP {
	@Value("${ftp.host}")
	private String host;
	
	@Value("${ftp.dir}")
	private String dir;
	
	@Value("${ftp.port}")
	private int port;
	
	@Value("${ftp.user}")
	private String user;
	
	@Value("${ftp.pass}")
	private String pass;
	
	private FTPClient ftp;
	
	private Logger logger = LoggerFactory.getLogger(ConnectToFTP.class);
	
	public void open() throws IOException {
		logger.info("Connecting to ftp server.");
		ftp = new FTPClient();

		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

		ftp.connect(host, port);
		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			throw new IOException("Exception in connecting to FTP Server");
		}

		ftp.login(user, pass);
	}
	
	public void upload(String fileName, File file) throws IOException {
		logger.info("Uploading {} to ftp server.", fileName);

		ftp.cwd(FTPCommands.PROTECT_BUFFER_SIZE.value);
		
		ftp.cwd(FTPCommands.PROTECTED_PRIVATE_CONNECTION.value);
		
		ftp.cwd(dir);
		
		ftp.cwd(FTPCommands.PREPARE_FOR_BINARY_DATA_TRANSFER.value);
		
		ftp.cwd(FTPCommands.ENTER_PASSIVE_SESSION.value);
		
		ftp.cwd(FTPCommands.STANDARTIZE_FORMAT_FOR_DIRECTORY_LISTING.value);

		if (file.exists()) {
			try (FileInputStream stream = new FileInputStream(file);){
				ftp.storeFile(fileName, stream);
			} catch (Exception e) {
				logger.error(e.getMessage(), e.getCause());
			}	
		}
	}
	
	private enum FTPCommands {
		PROTECT_BUFFER_SIZE("PBSZ 0"), 
		
		PROTECTED_PRIVATE_CONNECTION("PROT P"),
		
		PREPARE_FOR_BINARY_DATA_TRANSFER("TYPE I"),
		
		ENTER_PASSIVE_SESSION("PASV"),
		
		STANDARTIZE_FORMAT_FOR_DIRECTORY_LISTING("MLSD");
		
		private String value;
		
		FTPCommands(String value){
			this.value = value;
		}

		
	}
	
}
