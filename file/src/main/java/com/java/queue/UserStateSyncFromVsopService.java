package becp.interfacesvr.webservice.vsop.server;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.Queue;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.log4j.Logger;

import becp.interfacesvr.web.utils.ResultCode;
import becp.interfacesvr.webservice.vsop.model.ToVsopResponseXML;
import becp.interfacesvr.webservice.vsop.model.UserPayTypeSyncReqXml;
import becp.interfacesvr.webservice.vsop.model.UserStateSyncReqXml;
import becp.interfacesvr.webservice.vsop.proc.IUserStateProc;
import becp.interfacesvr.webservice.vsop.proc.impl.UserStateProc;
import becp.interfacesvr.webservice.vsop.util.ResponseToVsopUtil;
import btir.BtirException;
import cn.com.mbossvsop.www.vsop.VsopServiceRequest;
import cn.com.mbossvsop.www.vsop.VsopServiceResponse;
import cn.com.mbossvsop.www.vsop.userState.UserStateSyncToISMPSV_BindingSkeleton;

public class UserStateSyncFromVsopService extends UserStateSyncToISMPSV_BindingSkeleton{
	public Queue<String> userStateSyncQueue=QueueRegister.getUserStateSyncQueue();
	@Override
	public VsopServiceResponse userStateSyncToISMPSV(
			VsopServiceRequest parameters) throws RemoteException {
		logger.info("[userStateSyncToISMPSV][req]:"+parameters.getRequest());
		if(logger.isDebugEnabled()){
			logger.debug("[userStateSyncToISMPSV][req]:"+parameters.getRequest());
		}
		long time=System.currentTimeMillis();
		logger.info("start....."+time);
		String resXml = "";
		UserStateSyncReqXml rMsg = new UserStateSyncReqXml(parameters.getRequest());
		try {
			rMsg.splitAll();
		} catch (BtirException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String xml=parameters.getRequest();
		try{
			userStateSyncQueue.offer(xml);
		}catch(Exception e){
			e.printStackTrace();
		}
		resXml=ToVsopResponseXML.getBaseResponseXML(ResultCode.NORMAL,rMsg.getStreamingNo(),"success");
		VsopServiceResponse res = new VsopServiceResponse();
		/*try{
			String resXml2 = userStateProc.proc(parameters.getRequest());
		}catch(BtirException e){
			logger.error(e);
		}
		if(logger.isDebugEnabled()){
			logger.debug("[userStateSyncToISMPSV][res]:"+resXml);
		}*/
		res.setResponse(ResponseToVsopUtil.getResponse("UserStateSyncToISMPResp",resXml));
		logger.info("end....."+time);
		logger.debug("userStateSyncToISMPSV:"+res.getResponse());
		return res;
	}

	protected static final Logger logger = Logger.getLogger(UserStateSyncFromVsopService.class);
	private IUserStateProc userStateProc = null;
	
	public UserStateSyncFromVsopService() {
		super();
		userStateProc = new UserStateProc();
		
	}

}
