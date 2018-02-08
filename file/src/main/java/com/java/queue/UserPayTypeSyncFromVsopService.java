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
import becp.interfacesvr.webservice.vsop.model.UserPayTypeSyncReqXml;
import becp.interfacesvr.webservice.vsop.proc.IUserPayTypeProc;
import becp.interfacesvr.webservice.vsop.proc.impl.UserPayTypeProc;
import becp.interfacesvr.webservice.vsop.util.ResponseToVsopUtil;
import btir.BtirException;
import cn.com.mbossvsop.www.vsop.VsopServiceRequest;
import cn.com.mbossvsop.www.vsop.VsopServiceResponse;
import cn.com.mbossvsop.www.vsop.userPayType.UserPayTypeSyncToISMPSV_BindingSkeleton;

public class UserPayTypeSyncFromVsopService extends UserPayTypeSyncToISMPSV_BindingSkeleton{

	protected static final Logger logger = Logger.getLogger(UserPayTypeSyncFromVsopService.class);
	private IUserPayTypeProc userPayTypeProc = null;
	public Queue<String> userPayTypeSyncQueue=QueueRegister.getUserPayTypeSyncQueue();

	@Override
	public VsopServiceResponse userPayTypeSyncToISMPSV(
			VsopServiceRequest parameters) throws RemoteException {
		logger.info("[userPayTypeSyncToISMPSV][req]:"+parameters.getRequest());
		if(logger.isDebugEnabled()){
			logger.debug("[userPayTypeSyncToISMPSV][req]:"+parameters.getRequest());
		}
		long time=System.currentTimeMillis();
		logger.info("start....."+time);
		String resXml = "";
		UserPayTypeSyncReqXml rMsg = new UserPayTypeSyncReqXml(parameters.getRequest());
		try {
			rMsg.splitAll();
		} catch (BtirException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String xml=parameters.getRequest();
		try{
			userPayTypeSyncQueue.offer(xml);
		}catch(Exception e){
			e.printStackTrace();
		}
		resXml=ToVsopResponseXML.getBaseResponseXML(ResultCode.NORMAL,rMsg.getStreamingNo(),"success");
		VsopServiceResponse res = new VsopServiceResponse();
		/*try{
			String resXml2 = userPayTypeProc.proc(parameters.getRequest());
		}catch(BtirException e){
			logger.error(e);
		}
		if(logger.isDebugEnabled()){
			logger.debug("[userPayTypeSyncToISMPSV][res]:"+resXml);
		}*/
		res.setResponse(ResponseToVsopUtil.getResponse("UserPayTypeSyncToISMPResp",resXml));
		logger.info("end....."+time);
		logger.debug("userPayTypeSyncToISMPSV:"+res.getResponse());
		return res;
	}

	public UserPayTypeSyncFromVsopService() {
		super();
		userPayTypeProc = new UserPayTypeProc();
		
	}

}
