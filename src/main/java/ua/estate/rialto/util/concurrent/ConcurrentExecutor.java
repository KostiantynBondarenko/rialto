package ua.estate.rialto.util.concurrent;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Контейнер для размещения объектов синхронизации. Синхронизирует по объектам
 * типа K. Класс предназначен для использования в случаях, когда некая операция
 * относительно объекта синхронизации должна проходить только в одном потоке.
 * Блокировка будет происходить среди объектов в этом контейнере Например: у
 * двоих потоков есть список (у каждого потока свой)
 * сотрудников для обработки и сохранения в базу. При чём эти два списка содержат
 * несколько одних и тех же сотрудников. Необходимо исключить ситуацию, когда один
 * и тот же сотрудник будет обрабатываться в двух потоках одновременно.
 *
 * @param <K> Типы объектов, по которым будет происходить синхронизация. В типе должен быть
 *            переопределён метод <b>equals и hashcode</b> для сравнения объектов синхронизации
 *
 * @author dn221090bva, mixram on 10/11/16
 * @since 2.0
 */
@Component
public final class ConcurrentExecutor<K>  {

    private static final int INIT_SIZE = 10;

    private ConcurrentHashMap<K, K> syncObjImpl;

    private ConcurrentExecutor() {
        this.syncObjImpl = new ConcurrentHashMap<>(INIT_SIZE);
    }

    private <A, R> R innerSync(K syncBy,
                               Function<A, R> function,
                               A functionArg) {
        K curBlockObj = syncObjImpl.putIfAbsent(syncBy, syncBy);
        if (curBlockObj == null) {
            curBlockObj = syncBy;
        }
        synchronized(curBlockObj) {
            try {
                return function.apply(functionArg);
            } finally {
                syncObjImpl.remove(syncBy);
            }
        }
    }

    /**
     * Создаёт контейнер для размещения объектов синхронизации. Блокировка будет происходить среди объектов в этом контейнере.
     */
    public static ConcurrentExecutor newInstance() {
        return new ConcurrentExecutor();
    }

    /**
     * Выполняет функцию <code>function</code> с синхронизацией по объекту
     * <code>syncBy</code>. То есть гарантируется, что если несколько потоков
     * вызовут этот метод с одним и тем же объектом синхронизации <code>syncBy</code>,
     * их выполнение будет произведено <b>последовательно</b>.
     *
     * @param <A>         аргумент функции <code>function</code>
     * @param <R>         результат выполнения функции <code>function</code>. Он же -
     *                    возвращаемый результат этим методом
     * @param syncBy      объект синхронизации
     * @param function    функция, выполняющаяся внутри синхронизации по объекту
     *                    <code>syncBy</code>
     * @param functionArg аргумент функции
     *
     * @return результат выполнения функции <code>function</code>
     */
    public final <A, R> R execute(K syncBy,
                                  Function<A, R> function,
                                  A functionArg) {
        return innerSync(syncBy, function, functionArg);
    }

    /**
     * Выполняет функцию <code>function</code> с синхронизацией по объекту
     * <code>syncBy</code>. То есть гарантируется, что если несколько потоков
     * вызовут этот метод с одним и тем же объектом синхронизации <code>syncBy</code>,
     * их выполнение будет произведено <b>последовательно</b>.
     *
     * @param <R>      результат выполнения функции <code>function</code>. Он же -
     *                 возвращаемый результат этим методом
     * @param syncBy   объект синхронизации
     * @param function функция, выполняющаяся внутри синхронизации по объекту.
     *                 Функция вызывается с аргументом <b><code>null</code></b>
     *
     * @return результат выполнения функции <code>function</code>
     */
    public final <R> R executeNullArg(K syncBy,
                                      Function<?, R> function) {
        return innerSync(syncBy, function, null);
    }
}