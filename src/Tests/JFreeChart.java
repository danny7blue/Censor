/**
 * FileName: JFreeChart
 * Author:   DannyBlue
 * Date:     2018/9/22 17:09
 * Description: JFreeChart测试类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package Tests;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * 〈JFreeChart测试类〉
 *
 * @author DannyBlue
 * @create 2018/9/22
 * @since 1.0.0
 */
public class JFreeChart extends ApplicationFrame
{
    public JFreeChart(final String title )
    {
        super( title );
        final XYDataset dataset = createDataset( );
        final org.jfree.chart.JFreeChart chart = createChart( dataset );
        final ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 370 ) );
        chartPanel.setMouseZoomable( true , false );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset( )
    {
        final TimeSeries series = new TimeSeries( "Random Data" );
        Second current = new Second( );
        double value = 100.0;
        for (int i = 0; i < 4000; i++)
        {
            try
            {
                value = value + Math.random( ) - 0.5;
                series.add(current, new Double( value ) );
                current = ( Second ) current.next( );
            }
            catch ( SeriesException e )
            {
                System.err.println("Error adding to series");
            }
        }

        return new TimeSeriesCollection(series);
    }

    private org.jfree.chart.JFreeChart createChart(final XYDataset dataset )
    {
        return ChartFactory.createTimeSeriesChart(
                "Computing Test",
                "Seconds",
                "Value",
                dataset,
                false,
                false,
                false);
    }

    public static void main( final String[ ] args )
    {
        final String title = "Time Series Management";
        final JFreeChart demo = new JFreeChart( title );
        demo.pack( );
        RefineryUtilities.positionFrameRandomly( demo );
        demo.setVisible( true );
    }
}