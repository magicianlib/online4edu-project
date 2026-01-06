package io.ituknown.utils.exception;

/**
 * 单例类创建异常
 *
 * @author magicianlib@gmail.com
 * @since 2021/04/23 16:24
 */
public class SingletonCreationException extends ServiceException {

    public SingletonCreationException() {
        this("单例类实例化异常");
    }

    public SingletonCreationException(String message) {
        super(message);
    }

    public SingletonCreationException(Class<?> clazz) {
        this("单例类: \"" + clazz.getName() + "\" 不允许被实例化");
    }
}