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
import becp.interfacesvr.webservice.vsop.model.UserNoSyncReqXml;
import becp.interfacesvr.webservice.vsop.proc.IUserTelProc;
import becp.interfacesvr.webservice.vsop.proc.impl.UserTelProc;
import becp.interfacesvr.webservice.vsop.util.ResponseToVsopUtil;
import btir.BtirException;
import cn.com.mbossvsop.www.vsop.VsopServiceRequest;
import cn.com.mbossvsop.www.vsop.VsopServiceResponse;
import cn.com.mbossvsop.www.vsop.userNo.UserNoInfoSyncToISMPSV_BindingSkeleton;

public class UserNoSyncFromVsopService extends UserNoInfoSyncToISMPSV_BindingSkeleton{

	protected static final Logger logger = Logger.getLogger(UserNoSyncFromVsopService.class);
	private IUserTelProc userTelProc = null;
	public Queue<String> userNoSyncQueue=QueueRegister.getUserNoSyncQueue();
	public UserNoSyncFromVsopService() {
		super();
		userTelProc = new UserTelProc();
		
	}
	
	@Override
	public VsopServiceResponse userNoInfoSyncToISMPSV(
			VsopServiceRequest parameters) throws RemoteException {
		logger.info("[userNoInfoSyncToISMPSV][req]:"+parameters.getRequest());
		if(logger.isDebugEnabled()){
			logger.debug("[userNoInfoSyncToISMPSV][req]:"+parameters.getRequest());
		}
		long time=System.currentTimeMillis();
		logger.info("start....."+time);
		String resXml = "";
		UserNoSyncReqXml rMsg = new UserNoSyncReqXml(parameters.getRequest());
		try {
			rMsg.splitAll();
		} catch (BtirException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String xml=parameters.getRequest();
		userNoSyncQueue.offer(xml);
		resXml=ToVsopResponseXML.getBaseResponseXML(ResultCode.NORMAL,rMsg.getStreamingNo(),"success");
		VsopServiceResponse res = new VsopServiceResponse();
		/*try{
			String resXml2 = userTelProc.proc(parameters.getRequest());
		}catch(BtirException e){
			logger.error(e);
		}
		if(logger.isDebugEnabled()){
			logger.debug("[userNoInfoSyncToISMPSV][res]:"+resXml);
		}*/
		res.setResponse(ResponseToVsopUtil.getResponse("UserNoInfoSyncToISMPResp",resXml));
		logger.info("end....."+time);
		logger.debug("userNoInfoSyncToISMPSV:"+res.getResponse());
		return res;
	}

}
