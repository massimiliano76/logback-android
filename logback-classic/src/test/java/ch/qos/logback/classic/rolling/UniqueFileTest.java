/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2013, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package ch.qos.logback.classic.rolling;

import static junit.framework.Assert.assertTrue;

import ch.qos.logback.core.util.CachingDateFormatter;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.Test;

import ch.qos.logback.classic.ClassicTestConstants;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.rolling.ScaffoldingForRollingTests;
import ch.qos.logback.core.status.StatusChecker;
import ch.qos.logback.core.util.CoreTestConstants;

/**
 * Test that we can create time-stamped log files with the help of
 * the &lt;timestamp> element in configuration files.
 * 
 * @author Ceki G&uuml;lc&uuml;
 *
 */
public class UniqueFileTest {

  LoggerContext lc = new LoggerContext();
  StatusChecker sc = new StatusChecker(lc);
  Logger logger = lc.getLogger(this.getClass());


  void loadConfig(String confifFile) throws JoranException {
    JoranConfigurator jc = new JoranConfigurator();
    jc.setContext(lc);
    jc.doConfigure(confifFile);
  }

  @Test
  public void basic() throws Exception {
    loadConfig(ClassicTestConstants.JORAN_INPUT_PREFIX + "unique.xml");
    CachingDateFormatter sdf = new CachingDateFormatter("yyyyMMdd'T'HHmmss");
    String timestamp = sdf.format(System.currentTimeMillis());

    sc.assertIsErrorFree();

    Logger root = lc.getLogger(Logger.ROOT_LOGGER_NAME);
    root.info("hello");
    
    ScaffoldingForRollingTests.existenceCheck(CoreTestConstants.OUTPUT_DIR_PREFIX+"TS_"+timestamp+"log.txt");
  }
}
