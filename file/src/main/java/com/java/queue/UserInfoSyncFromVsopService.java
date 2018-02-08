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
import becp.interfacesvr.webservice.vsop.model.UserInfoSyncReqXml;
import becp.interfacesvr.webservice.vsop.proc.IUserInfoProc;
import becp.interfacesvr.webservice.vsop.proc.impl.UserInfoProc;
import becp.interfacesvr.webservice.vsop.util.ResponseToVsopUtil;
import btir.BtirException;
import cn.com.mbossvsop.www.vsop.VsopServiceRequest;
import cn.com.mbossvsop.www.vsop.VsopServiceResponse;
import cn.com.mbossvsop.www.vsop.userInfo.UserInfoSyncToISMPSV_BindingSkeleton;

public class UserInfoSyncFromVsopService extends
		UserInfoSyncToISMPSV_BindingSkeleton {

	protected static final Logger logger = Logger
			.getLogger(UserInfoSyncFromVsopService.class);
	private IUserInfoProc userInfoProc = null;
	public Queue<String> userInfoSyncQueue=QueueRegister.getUserInfoSyncQueue();

	public UserInfoSyncFromVsopService() {
		super();
		userInfoProc = new UserInfoProc();
	}

	public VsopServiceResponse userInfoSyncToISMPSV(
			VsopServiceRequest parameters) throws RemoteException {
		logger.info("[userInfoSyncToISMPSV][req]:"
				+ parameters.getRequest());
		if (logger.isDebugEnabled()) {
			logger.debug("[userInfoSyncToISMPSV][req]:"
					+ parameters.getRequest());
		}
		long time=System.currentTimeMillis();
		logger.info("start....."+time);
		String resXml = "";
		UserInfoSyncReqXml rMsg = new UserInfoSyncReqXml(parameters.getRequest());
		try {
			rMsg.splitAll();
		} catch (BtirException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String xml=parameters.getRequest();
		userInfoSyncQueue.offer(xml);
		resXml=ToVsopResponseXML.getBaseResponseXML(ResultCode.NORMAL,rMsg.getStreamingNo(),"success");
		VsopServiceResponse res = new VsopServiceResponse();
		/*try {
			String resXml2 = userInfoProc.proc(parameters.getRequest());
		} catch (BtirException e) {
			logger.error(e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("[userInfoSyncToISMPSV][res]:" + resXml);
		}*/
		res.setResponse(ResponseToVsopUtil.getResponse("UserInfoSyncToISMPResp",resXml));
		logger.info("end....."+time);
		logger.debug("userInfoSyncToISMPSV:" + res.getResponse());
		return res;
	}

}
