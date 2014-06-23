import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MyTimer {

	private ScheduledExecutorService timer;
	private final int NOTIFICATION_INTERVAL = 60;
	
	public MyTimer() {
		timer = Executors.newScheduledThreadPool(5);
	}
	
	public void createTimer(BuffTimer buffTimer) {
		timer.scheduleAtFixedRate(buffTimer, 0, NOTIFICATION_INTERVAL, TimeUnit.SECONDS);
	}
	
	public void createTimer(BuffTimer buffTimer, int startInterval) {
		timer.scheduleAtFixedRate(buffTimer, startInterval, NOTIFICATION_INTERVAL, TimeUnit.SECONDS);
	}
}
