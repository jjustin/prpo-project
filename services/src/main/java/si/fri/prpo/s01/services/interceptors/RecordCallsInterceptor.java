package si.fri.prpo.s01.services.interceptors;

import si.fri.prpo.s01.services.annotations.RecordCalls;
import si.fri.prpo.s01.services.beans.RecordCallsBean;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@RecordCalls
public class RecordCallsInterceptor {

    @Inject
    private RecordCallsBean recordCallsBean;

    @AroundInvoke
    public Object recordCalls(InvocationContext context) throws Exception{

        String methodName = context.getMethod().getName();
        recordCallsBean.increase(methodName);

        return context.proceed();
    }
}
