// ALIADA - Automatic publication under Linked Data paradigm
//          of library and museum data
//
// Component: aliada-user-interface
// Responsible: ALIADA Consortium

package eu.aliada.gui.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

/**
 * This class is the retrieve image result.
 * @author xabier
 * @version $Revision: 1.1 $, $Date: 2015/01/28 15:20:54 $
 * @since 1.0
 */

public class CustomImageBytesResult implements Result{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(final ActionInvocation invocation) throws Exception {
		
		ImageAction action = (ImageAction) invocation.getAction();
		HttpServletResponse response = ServletActionContext.getResponse();
 
		response.setContentType(action.getCustomContentType());
		response.getOutputStream().write(action.getCustomImageInBytes());
		response.getOutputStream().flush();
		
	}

}
