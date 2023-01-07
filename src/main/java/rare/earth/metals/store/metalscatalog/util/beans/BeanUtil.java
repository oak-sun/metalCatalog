package rare.earth.metals.store.metalscatalog.util.beans;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BeanUtil {
	@Autowired
	private BeanUtilsBean bean;

	public void copyProperties(final Object dest,
							   final Object orig) {
		try {
			bean.copyProperties(dest, orig);

		} catch (IllegalAccessException |
				 InvocationTargetException e) {

			log.error(ExceptionUtils.getStackTrace(e));
		}
	}
}
