package becp.interfacesvr.webservice.vsop.server;

import java.util.Queue;

import org.apache.log4j.Logger;

import becp.interfacesvr.webservice.vsop.proc.IUserInfoProc;
import becp.interfacesvr.webservice.vsop.proc.IUserPayTypeProc;
import becp.interfacesvr.webservice.vsop.proc.IUserStateProc;
import becp.interfacesvr.webservice.vsop.proc.IUserTelProc;
import becp.interfacesvr.webservice.vsop.proc.impl.UserInfoProc;
import becp.interfacesvr.webservice.vsop.proc.impl.UserPayTypeProc;
import becp.interfacesvr.webservice.vsop.proc.impl.UserStateProc;
import becp.interfacesvr.webservice.vsop.proc.impl.UserTelProc;
import btir.BtirException;

public class UserSyncTask implements Runnable{
	protected static final Logger logger = Logger.getLogger(UserSyncTask.class);
	private IUserInfoProc userInfoProc = null;
	private IUserTelProc userTelProc = null;
	private IUserPayTypeProc userPayTypeProc = null;
	private IUserStateProc userStateProc = null;
	public Queue<String> userInfoSyncQueue=QueueRegister.getUserInfoSyncQueue();
	public Queue<String> userNoSyncQueue=QueueRegister.getUserNoSyncQueue();
	public Queue<String> userPayTypeSyncQueue=QueueRegister.getUserPayTypeSyncQueue();
	public Queue<String> userStateSyncQueue=QueueRegister.getUserStateSyncQueue();
	public UserSyncTask(){
		this.userInfoProc=new UserInfoProc();
		this.userTelProc=new UserTelProc();
		this.userPayTypeProc=new UserPayTypeProc();
		this.userStateProc=new UserStateProc();
	}
	@Override
	public void run() {
		if(!userInfoSyncQueue.isEmpty()){
			logger.info("deal userInfo");
			String reqXml=userInfoSyncQueue.poll();
			try {
				userInfoProc.proc(reqXml);
			} catch (BtirException e) {
				userInfoSyncQueue.offer(reqXml);
				e.printStackTrace();
			}
		}
		if(!userNoSyncQueue.isEmpty()){
			logger.info("deal userNo");
			String reqXml=userNoSyncQueue.poll();
			try {
				userTelProc.proc(reqXml);
			} catch (BtirException e) {
				userNoSyncQueue.offer(reqXml);
				e.printStackTrace();
			}
		}
		if(!userPayTypeSyncQueue.isEmpty()){
			logger.info("deal userPayType");
			String reqXml=userPayTypeSyncQueue.poll();
			try {
				userPayTypeProc.proc(reqXml);
			} catch (BtirException e) {
				userPayTypeSyncQueue.offer(reqXml);
				e.printStackTrace();
			}
		}
		if(!userStateSyncQueue.isEmpty()){
			logger.info("deal userState");
			String reqXml=userStateSyncQueue.poll();
			try {
				userStateProc.proc(reqXml);
			} catch (BtirException e) {
				userStateSyncQueue.offer(reqXml);
				e.printStackTrace();
			}
		}

		
	}

}
