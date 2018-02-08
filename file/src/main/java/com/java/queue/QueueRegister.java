package becp.interfacesvr.webservice.vsop.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueRegister {
	private static  Queue<String> userInfoSyncQueue=new ConcurrentLinkedQueue<String>();    
	private static  Queue<String> userNoSyncQueue=new ConcurrentLinkedQueue<String>();    
	private static  Queue<String> userPayTypeSyncQueue=new ConcurrentLinkedQueue<String>();    
	private static  Queue<String> userStateSyncQueue=new ConcurrentLinkedQueue<String>();
	public static Queue<String> getUserInfoSyncQueue() {
		return userInfoSyncQueue;
	}
	public static void setUserInfoSyncQueue(ConcurrentLinkedQueue<String> userInfoSyncQueue) {
		QueueRegister.userInfoSyncQueue = userInfoSyncQueue;
	}
	public static Queue<String> getUserNoSyncQueue() {
		return userNoSyncQueue;
	}
	public static void setUserNoSyncQueue(ConcurrentLinkedQueue<String> userNoSyncQueue) {
		QueueRegister.userNoSyncQueue = userNoSyncQueue;
	}
	public static Queue<String> getUserPayTypeSyncQueue() {
		return userPayTypeSyncQueue;
	}
	public static void setUserPayTypeSyncQueue(ConcurrentLinkedQueue<String> userPayTypeSyncQueue) {
		QueueRegister.userPayTypeSyncQueue = userPayTypeSyncQueue;
	}
	public static Queue<String> getUserStateSyncQueue() {
		return userStateSyncQueue;
	}
	public static void setUserStateSyncQueue(ConcurrentLinkedQueue<String> userStateSyncQueue) {
		QueueRegister.userStateSyncQueue = userStateSyncQueue;
	}
}
