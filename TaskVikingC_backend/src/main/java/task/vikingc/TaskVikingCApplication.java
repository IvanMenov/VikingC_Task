package task.vikingc;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import task.vikingc.services.Organizer;

@SpringBootApplication
public class TaskVikingCApplication implements CommandLineRunner {
	
	@Autowired
	private Organizer organizer;

	@Autowired
	Environment env;
	
	public static void main(String[] args) {
		SpringApplication.run(TaskVikingCApplication.class, args);
	}
	private ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
	
	private Logger logger = LoggerFactory.getLogger(TaskVikingCApplication.class);
	@Override
	public void run(String... args) throws Exception {
		boolean isAutomatic = env.getProperty("isAutomatic") != null ? Boolean.parseBoolean(env.getProperty("isAutomatic")) : false;
		String timeToRunAt =  env.getProperty("time");
		String intervalToRunAt = env.getProperty("interval");
		
		logger.info("Starting up the application");
		
		if(isAutomatic) {
			scheduler.initialize();
			scheduler.setAwaitTerminationSeconds(4);
			
			logger.info("Enabled scheduling for file uploads");
			logger.info("Starting the application with the following parameters : {} , {} , {}", isAutomatic, timeToRunAt, intervalToRunAt);
			
			if(timeToRunAt != null) {
				String hours = null;
				String minutes= null;
				try {
					String[] time = timeToRunAt.split(":");
					 hours = time[0];
					 minutes = time[1];
				} catch (Exception e) {
					logger.error("Incorrect format for time. Propper format is hh:mm (8:30, 19:25 etc.)");
					logger.error(e.getMessage(), e.getCause());
				}
				
				String expression = String.format("0 %s %s * * ?", minutes, hours);
				CronTrigger trigger = new CronTrigger(expression);
				
				scheduler.schedule(() -> {
					try {
						organizer.organizeFromCSV();
					} catch (IOException e) {
						e.printStackTrace();
					}
					}, trigger);
			}
			else {
				long interval = 60000;
				try {
					interval = Long.parseLong(intervalToRunAt);		

				} catch (Exception e) {
					logger.error(e.getMessage(), e.getCause());
				}
				scheduler.scheduleAtFixedRate(() -> {try {
					organizer.organizeFromCSV();
				} catch (IOException e) {
					e.printStackTrace();
				}},Instant.now().plusMillis(interval), Duration.ofSeconds(interval/1000));
			}
		}else {
			logger.info("The application will run in manual mode only!");
		}
			
	}
	
	@PreDestroy
	public void onExit() {
		scheduler.shutdown();
	}
}
