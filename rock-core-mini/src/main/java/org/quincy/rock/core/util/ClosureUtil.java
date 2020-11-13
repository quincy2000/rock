package org.quincy.rock.core.util;

import java.util.Collection;
import java.util.Iterator;

import org.quincy.rock.core.function.Consumer;

/**
 * <b>ClosureUtil。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>wks</td><td>2018年4月19日 下午3:06:02</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author wks
 * @since 1.0
 */
public class ClosureUtil {
	/**
	 * <b>处理多层次(父子关系深层嵌套)对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 将所有源对象转换成目标对象并放在root中。
	 * 在闭包中存放对象的处理和转换逻辑
	 * @param <S> 源类型
	 * @param <D> 目标类型
	 * @param c 源对象集合
	 * @param root 存放所有目标对象的容器对象
	 * @param closure 处理对象的闭包
	 */
	public static <S, D> void processMultiObject(Collection<S> c, D root, MultiObjectClosure<S, D> closure) {
		if (c == null)
			return;
		for (S s : c) {
			D d = closure.process(s, root);
			if (d != null)
				processMultiObject(closure.getSub(s), d, closure);
		}
	}

	/**
	 * <b>处理多层次(父子关系深层嵌套)对象的闭包。</b><br>
	 * S: 源对象类型 <br>
	 * D: 目标对象类型
	 * @version 1.0
	 * @author wks
	 * @since 1.0
	 */
	public static interface MultiObjectClosure<S, D> {
		/**
		 * <b>获得对象的孩子列表。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param parent 父对象
		 * @return 孩子列表
		 */
		public Collection<S> getSub(S parent);

		/**
		 * <b>将child转换成目标类型并处理孩子和父亲的关系。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 。
		 * @param child 孩子
		 * @param parent 父亲
		 * @return 目标对象
		 */
		public D process(S child, D parent);
	}

	/**
	 * <b>处理多级代码。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 用递归实现，将按升序排列的多级代码对象组合成树状结构。
	 * @param <S> source object
	 * @param <D> destination object
	 * @param its 排序列表迭代器
	 * @param root 要处理的当前根对象
	 * @param closure 处理单个对象的闭包
	 * @return 待处理的下一个对象，返回null代表到达结尾
	 */
	public static <S, D> S processMultiCode(Iterator<S> its, D root, MultiCodeClosure<S, D> closure) {
		//要处理的子对象级别
		int level = closure.getDestLevel(root) + 1;

		//首先获得第一个子对象
		S obj = null;
		if (its.hasNext()) {
			obj = its.next();
		}
		//如果有子部门则开始循环，否则说明该部门是兄弟部门或长辈部门,交给上一层递归处理
		while (obj != null) {
			int objLevel = closure.getSrcLevel(obj);
			if (objLevel < level) {
				break;
			}
			D d = closure.process(obj, root);
			if (d != null)
				obj = processMultiCode(its, d, closure);
		}
		return obj;
	}

	/**
	 * <b>多级代码处理闭包。</b>
	 * @version 1.0
	 * @author wangkunshan
	 * @since 1.0
	 */
	public static interface MultiCodeClosure<S, D> {
		/**
		 * <b>获得源对象的代码级别。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param src 源对象
		 * @return 源对象的代码级别
		 */
		public int getSrcLevel(S src);

		/**
		 * <b>获得目标对象的代码级别。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param src 目标对象
		 * @return 目标对象的代码级别
		 */
		public int getDestLevel(D dest);

		/**
		 * <b>处理源对象。</b>
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param src 源对象
		 * @param root 存放目标对象的父对象
		 * @return 根据源对象转化的目标对象
		 */
		public D process(S src, D parent);
	}

	/**
	 * <b>处理嵌套依赖(多个)。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param <S> source object
	 * @param <D> destination object
	 * @param src 源对象集合
	 * @param d 存放所有目标对象的容器对象
	 * @param closure 处理对象的闭包
	 */
	public static <S, D> void processNestDepends(Collection<S> src, D dest, MultiDependClosure<S, D> closure) {
		if (src == null)
			return;
		for (S s : src) {
			processNestDepends(closure.getDepends(s), dest, closure);
			closure.process(s, dest);
		}
	}

	/**
	 * <b>嵌套依赖对象(多个)的处理闭包。</b>
	 * @version 1.0
	 * @author wks
	 * @since 1.0
	 */
	public static interface MultiDependClosure<S, D> {
		/**
		 * <b>获得依赖集合。</b>  
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param src 源对象
		 * @return 源对象依赖的对象集合
		 */
		public Collection<S> getDepends(S src);

		/**
		 * <b>处理源对象和目标对象的关系。</b>  
		 * <p><b>详细说明：</b></p>
		 * <!-- 在此添加详细说明 -->
		 * 无。
		 * @param src 源对象
		 * @param dest 目标对象
		 */
		public void process(S src, D dest);
	}

	/**
	 * <b>将对象添加到集合中，包括所有级联父对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param set 容器集合
	 * @param vo 实现了HasParent接口的对象
	 */
	public static <T extends HasParent<T>> void addToSet(Collection<T> set, T vo) {
		set.add(vo);
		vo = vo.getParent();
		while (vo != null) {
			set.add(vo);
			vo = vo.getParent();
		}
	}

	/**
	 * <b>将源集合中的所有对象添加到目标集合中，包括所有级联子对象。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param set 目标容器集合
	 * @param vos 实现了HasChildren接口的对象集合
	 */
	public static <T extends HasChildren<T>> void addToSet(Collection<T> destSet, Collection<T> vos) {
		for (T vo : vos) {
			destSet.add(vo);
			addToSet(destSet, vo.getChildren());
		}
	}

	/**
	 * <b>循环迭代Tree。</b>
	 * <p><b>详细说明：</b></p>
	 * <!-- 在此添加详细说明 -->
	 * 无。
	 * @param tree 树枝
	 * @param forward 正向遍历
	 * @param consumer 回调处理函数
	 */
	public static <T extends HasChildren<T>> void forEach(T tree, boolean forward, Consumer<T> consumer) {
		if (forward)
			consumer.call(tree);
		if (tree.hasChildren()) {
			for (T child : tree.getChildren()) {
				forEach(child, forward, consumer);
			}
		}
		if (!forward)
			consumer.call(tree);
	}
}
