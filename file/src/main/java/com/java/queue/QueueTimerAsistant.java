package becp.interfacesvr.webservice.vsop.server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;

public class QueueTimerAsistant implements InitializingBean{
	@Override
	public void afterPropertiesSet() throws Exception {
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(5);
		schedule.scheduleAtFixedRate(new UserSyncTask(), 1l, 100l,TimeUnit.MILLISECONDS);
	}  
}  
