package Tests;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import _ES._ES.DefectDetection;

@RunWith(JUnitPlatform.class)
@SelectClasses({AppTest.class,BarChartTest.class,DatatableTest.class,DefectCountTest.class,DefectDetection.class,ExcelHandlerTest.class,MainFrameTest.class,PieChartTest.class,ThresHoldTest.class})
public class AllTests {

}
