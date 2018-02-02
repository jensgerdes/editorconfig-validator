package eu.b1n4ry.editorconfig.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception thrown when a problem with the instantiation of the {@link eu.b1n4ry.editorconfig.check.Check} classes
 * occurred.
 */
public class CheckInstantiationException extends RuntimeException {

	private static final Logger LOG = LoggerFactory.getLogger(CheckInstantiationException.class);

	public CheckInstantiationException(ReflectiveOperationException cause) {
		super(cause);
		LOG.trace("Initialized CheckInstantiationException({}).", cause);
	}
}
