package org.quincy.rock.core.dao;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.quincy.rock.core.exception.RockException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 * <b>RockLocalContainerEntityManagerFactoryBean。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年3月6日 下午4:07:30</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class RockLocalContainerEntityManagerFactoryBean extends LocalContainerEntityManagerFactoryBean {
	/** 
	 * setMappingResources。
	 * @see org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean#setMappingResources(java.lang.String[])
	 */
	@Override
	public void setMappingResources(String... mappingResources) {
		if (ArrayUtils.isEmpty(mappingResources)) {
			super.setMappingResources(mappingResources);
		} else {
			Set<String> mrSet = new HashSet<>();
			Resource[] rs = null;
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			for (String resource : mappingResources) {
				int prefixEnd = resource.startsWith("war:") ? resource.indexOf("*/") + 1 : resource.indexOf(":") + 1;
				resource = resource.substring(prefixEnd);
				if (resolver.getPathMatcher().isPattern(resource) && resource.endsWith(".xml")) {
					//是模式匹配资源
					String location = resource.substring(0, resource.lastIndexOf("/") + 1);
					try {
						rs = resolver
								.getResources(PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + resource);
					} catch (IOException e) {
						throw new RockException(e.getMessage(), e);
					}
					for (Resource r : rs) {
						mrSet.add(location + r.getFilename());
					}
				} else {
					mrSet.add(resource);
				}
			}
			super.setMappingResources(mrSet.toArray(new String[mrSet.size()]));
		}
	}
}
