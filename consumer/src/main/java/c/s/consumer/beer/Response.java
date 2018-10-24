/**
 * 
 */
package c.s.consumer.beer;

/**
 *
 * @author chineshine
 * @date 2018年10月23日
 */
public class Response {
	public ResponseStatus status;
}

enum ResponseStatus {
	OK, NOT_OK
}