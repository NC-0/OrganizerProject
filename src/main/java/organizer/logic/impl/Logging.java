package organizer.logic.impl;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

public class Logging {

	private Logger log = Logger.getLogger(getClass());

	public void log(Exception ex){
		StackTraceElement stackTraceElement = ex.getStackTrace()[2];
		log.error(stackTraceElement.getClassName()+";"+
			stackTraceElement.getMethodName()+";"+
			stackTraceElement.getLineNumber()+";"+
			ex.getMessage());
	}

	public void logBefore(JoinPoint joinPoint){
		log.info("-------------------------------------");
		Object[] signatureArgs = joinPoint.getArgs();
		for (Object signatureArg: signatureArgs) {
			log.info(joinPoint.getSignature().getName()+";arg: " + signatureArg);
		}
	}
}
