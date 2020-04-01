package com.sibo.business.utils;

import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.sibo.business.calculate.AnalysisFactory;
import com.sibo.business.calculate.AnalysisFactoryTest;
import com.sibo.business.calculate.impl.*;
import com.sibo.business.entity.VSourceRecord;
import com.sibo.business.entity.VStandard;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 导入原始记录解析类
 */
public class ImportExcelUtils {
    private  static final String WD = "wd";//温度
    private  static final String FDJZS = "fdjzs";//发送机转速
    private  static final String DYZS = "dyzs";//DY转速
    private  static final String DYZQD = "dyzqd";//DY准确度
    private  static final String JL = "jl";//距离
    private  static final String NJ = "nj";//扭矩
    private  static final String FJCSCZLCY = "fjcsczlcy";//非接触式车载路试仪
    private  static final String FS = "fs";//风速
    private  static final String HSJZJSYT = "hsjzjsyt";//后视镜撞击试验台
    private  static final String HSJWQSYT = "hsjwqsyt";//后视镜弯曲试验台
    private  static final String  QJY= "qjy";//求积仪
    private  static final String  RYXZJSYT= "ryxzjsyt";//燃油箱撞击试验台
    private  static final String  NQPLSYT= "nqplsyt";//挠曲疲劳试验机
    private  static final String  WJKZDZWNSYJ= "wjkzdzwnsyj";//微机控制电子万能试验机
    private  static final String  SYQ= "syq";//试验球
    private  static final String  SYB= "syb";//试验帮
    private  static final String  SYX= "syx";//试验线
    private  static final String  SYZZ= "syzz";//试验直指
    private  static final String  SYZ= "syz";//试验指
    private  static final String  SYZHEN= "syzhen";//试验针
    private  static final String  SYXIAO= "syxiao";//试验销
    private  static final String  BZSYZ= "bzsyz";//标准试验指
    private  static final String  DXSYTB= "dxsytb";//D型试验探棒
    private  static final String  BXSYWZ= "bxsywz";//B型试验弯指
    private  static final String  QYSYZZ= "qysyzz";//球压试验装置
    private  static final String  ZYSSSY= "zysssy";//针焰丝试验仪
    private  static final String  ZRSSYY= "zrssyy";//灼热丝试验仪
    private  static final String  GFSYX= "gfsyx";//鼓风试验箱
    private  static final String  ZNSZSDTNJY= "znszsdtnjy";//智能数字式灯头扭矩仪
    private  static final String  JDCHWMDSYJ= "jdchwmdsyj";//机动车恒温脉动试验机
    private  static final String  GTDLSYJ= "gtdlsyj";//滚筒跌落试验机
    private  static final String  ZDSYLSX= "zdsylsx";//制动试验淋水箱
    private  static final String  DGZJSYT= "dgzjsyt";//灯光转角试验台
    private  static final String  DHHZSSYJ= "dhhzssyj";//电痕化指数试验机
    private  static final String  WDXSYT= "wdxsyt";//稳定性试验台
    private  static final String  WKQSMSYY= "wkqsmsyy";//温控器寿命试验仪
    private  static final String  FZLSYJ= "fzlsyj";//附着力试验机
    private  static final String  LYSYX= "lysyx";//淋雨试验箱
    private  static final String  DYXLLNZSYJ= "dyxllnzsyj";//电源线拉力扭转试验机
    private  static final String  JDDYXWQNZSYJ= "jddyxwqnzsyj";//家电电源线弯曲试验机
    private  static final String  JSS= "jss";//金属丝
    private  static final String  KDQDSYZZ= "kdqdsyzz";//抗电强度实验装置
    private  static final String  TLSYZ= "tlsyz";//推力试验指
    private  static final String  SYTT= "sytt";//试验探头
    private  static final String  ZRRYSYY= "zrrysyy";//灼热燃油试验仪
    private  static final String  ZDRGLG= "zdrglg";//制动软管量规
    private  static final String  LQCJSYT= "lqcjsyt";//落球冲击试验台
    private  static final String  QXJJ100= "qxjj100";//球形检具 100mm
    private  static final String  QXJJ= "qxjj";//球形检具
    private  static final String  WFNYJJ= "wfnyjj";//外方内圆检具
    private  static final String  SWHDCLZZ= "swhdclzz";//三维H点测量装置
    private  static final String  SYCSZZ= "sycszz";//视野测试装置
    private  static final String  WBTCWJJD= "wbtcwjjd";//外部凸出物检具（大）
    private  static final String  WBTCWJJX= "wbtcwjjx";//外部凸出物检具（小）
    private  static final String  WBTCW= "wbtcw";//外部突出物
    private  static final String  ZDPB= "zdpb";//振动平板
    private  static final String  BZHP= "bzhp";//标准号牌
    private  static final String  ZYKC= "zykc";//专用卡尺


//    private static  AnalysisFactory analysisFactory;



    public static Map  importExcel(String filePath,String sourceType) throws BusinessRuntimeException{
        try {
            Map map = new HashMap<>();
            InputStream is = new FileInputStream(filePath.substring(8));
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            XSSFRow titleCell = xssfSheet.getRow(0);
            List<VSourceRecord> list = new ArrayList<>();
            // 可用如下方式简化后期待优化2019.1.24
//            analysisFactory = AnalysisFactoryTest.getInstance().creator(sourceType);
//            return analysisFactory.importExcel(xssfSheet,map,list);

            //其他
            switch (sourceType){
                case WD:
                    AnalysisFactory im = new TemperatureImpl();
                    return im.importExcel(xssfSheet,map,list);
                case FDJZS:
                    AnalysisFactory engineSpeedImpl = new EngineSpeedImpl();
                    return engineSpeedImpl.importExcel(xssfSheet,map,list);
                case DYZS:
                    AnalysisFactory dYRotateSpeedImpl = new DYRotateSpeedImpl();
                    return dYRotateSpeedImpl.importExcel(xssfSheet,map,list);
                case DYZQD:
                    AnalysisFactory dYRotateSpeedAccuracyRatingImpl = new DYRotateSpeedAccuracyRatingImpl();
                    return dYRotateSpeedAccuracyRatingImpl.importExcel(xssfSheet,map,list);
                case JL:
                    AnalysisFactory distanceImpl = new DistanceImpl();
                    return distanceImpl.importExcel(xssfSheet,map,list);
                case NJ:
                    AnalysisFactory torqueImpl = new TorqueImpl();
                    return torqueImpl.importExcel(xssfSheet,map,list);
                case FJCSCZLCY:
                    AnalysisFactory roadTestInstrumentImpl = new RoadTestInstrumentImpl();
                    return roadTestInstrumentImpl.importExcel(xssfSheet,map,list);
                case NQPLSYT:
                    AnalysisFactory fatigueUnderFlexingImpl = new FatigueUnderFlexingImpl();
                    return fatigueUnderFlexingImpl.importExcel(xssfSheet,map,list);
                case RYXZJSYT:
                    AnalysisFactory fuelTankCollisionImpl = new FuelTankCollisionImpl();
                    return fuelTankCollisionImpl.importExcel(xssfSheet,map,list);
                case QJY:
                    AnalysisFactory planimeterImpl = new PlanimeterImpl();
                    return planimeterImpl.importExcel(xssfSheet,map,list);
                case FS:
                    AnalysisFactory windSpeedImpl = new WindSpeedImpl();
                    return windSpeedImpl.importExcel(xssfSheet,map,list);
                case HSJZJSYT:
                    AnalysisFactory rearviewMirrorImpact = new RearviewMirrorImpact();
                    return rearviewMirrorImpact.importExcel(xssfSheet,map,list);
                case HSJWQSYT:
                    AnalysisFactory rearviewMirrorBentImpl = new RearviewMirrorBentImpl();
                    return rearviewMirrorBentImpl.importExcel(xssfSheet,map,list);
                case SYQ:
                    AnalysisFactory testTheBallImpl = new TestTheBallImpl();
                    return testTheBallImpl.importExcel(xssfSheet,map,list);
                case WJKZDZWNSYJ:
                    AnalysisFactory microcomputerControlledElectronicUniversalTestingMachineImpl = new MicrocomputerControlledElectronicUniversalTestingMachineImpl();
                    return microcomputerControlledElectronicUniversalTestingMachineImpl.importExcel(xssfSheet,map,list);
                case SYB:
                    AnalysisFactory testBarImpl = new TestBarImpl();
                    return testBarImpl.importExcel(xssfSheet,map,list);
                case SYX:
                    AnalysisFactory testWireImpl = new TestWireImpl();
                    return testWireImpl.importExcel(xssfSheet,map,list);
                case SYZZ:
                    AnalysisFactory testPointsImpl = new TestPointsImpl();
                    return testPointsImpl.importExcel(xssfSheet,map,list);
                case SYZ:
                    AnalysisFactory testFingerImpl = new TestFingerImpl();
                    return testFingerImpl.importExcel(xssfSheet,map,list);
                case SYZHEN:
                    AnalysisFactory testTheNeedleImpl = new TestTheNeedleImpl();
                    return testTheNeedleImpl.importExcel(xssfSheet,map,list);
                case SYXIAO:
                    AnalysisFactory theTestPinImpl = new TheTestPinImpl();
                    return theTestPinImpl.importExcel(xssfSheet,map,list);
                case BZSYZ:
                    AnalysisFactory standardTestIndexImpl = new StandardTestIndexImpl();
                    return standardTestIndexImpl.importExcel(xssfSheet,map,list);
                case DXSYTB:
                    AnalysisFactory testSondeImpl = new TestSondeImpl();
                    return testSondeImpl.importExcel(xssfSheet,map,list);
                case BXSYWZ:
                    AnalysisFactory bendTestRefersImpl = new BendTestRefersImpl();
                    return bendTestRefersImpl.importExcel(xssfSheet,map,list);
                case QYSYZZ:
                    AnalysisFactory ballPressureTestEquipmentImpl = new BallPressureTestEquipmentImpl();
                    return ballPressureTestEquipmentImpl.importExcel(xssfSheet,map,list);
                case ZYSSSY:
                    AnalysisFactory needleFlameWireTesterImpl = new NeedleFlameWireTesterImpl();
                    return needleFlameWireTesterImpl.importExcel(xssfSheet,map,list);
                case ZRSSYY:
                    AnalysisFactory glowWireTesterImpl = new GlowWireTesterImpl();
                    return glowWireTesterImpl.importExcel(xssfSheet,map,list);
                case GFSYX:
                    AnalysisFactory blastChamberImpl = new BlastChamberImpl();
                    return blastChamberImpl.importExcel(xssfSheet,map,list);
                case ZNSZSDTNJY:
                    AnalysisFactory intelligentDigitalLampHeadTorqueMeterImpl = new IntelligentDigitalLampHeadTorqueMeterImpl();
                    return intelligentDigitalLampHeadTorqueMeterImpl.importExcel(xssfSheet,map,list);
                case JDCHWMDSYJ:
                    AnalysisFactory motorVehicleConstantTemperaturePulsationTestingMachineImpl = new MotorVehicleConstantTemperaturePulsationTestingMachineImpl();
                    return motorVehicleConstantTemperaturePulsationTestingMachineImpl.importExcel(xssfSheet,map,list);
                case GTDLSYJ:
                    AnalysisFactory rollerDropTestingMachineImpl = new RollerDropTestingMachineImpl();
                    return rollerDropTestingMachineImpl.importExcel(xssfSheet,map,list);
                case ZDSYLSX:
                    AnalysisFactory brakeTestShowerTankImpl = new BrakeTestShowerTankImpl();
                    return brakeTestShowerTankImpl.importExcel(xssfSheet,map,list);
                case DGZJSYT:
                    AnalysisFactory lightAngleTestBedImpl = new LightAngleTestBedImpl();
                    return lightAngleTestBedImpl.importExcel(xssfSheet,map,list);
                case DHHZSSYJ:
                    AnalysisFactory electricTraceIndexTesterImpl = new ElectricTraceIndexTesterImpl();
                    return electricTraceIndexTesterImpl.importExcel(xssfSheet,map,list);
                case WDXSYT:
                    AnalysisFactory stabilityTestStandImpl = new StabilityTestStandImpl();
                    return stabilityTestStandImpl.importExcel(xssfSheet,map,list);
                case WKQSMSYY:
                    AnalysisFactory thermostatLifeTesterImpl = new ThermostatLifeTesterImpl();
                    return thermostatLifeTesterImpl.importExcel(xssfSheet,map,list);
                case FZLSYJ:
                    AnalysisFactory adhesionTestingMachineImpl = new AdhesionTestingMachineImpl();
                    return adhesionTestingMachineImpl.importExcel(xssfSheet,map,list);
                case LYSYX:
                    AnalysisFactory rainChamberImpl = new RainChamberImpl();
                    return rainChamberImpl.importExcel(xssfSheet,map,list);
                case JSS:
                    AnalysisFactory wireImpl = new WireImpl();
                    return wireImpl.importExcel(xssfSheet,map,list);
                case KDQDSYZZ:
                    AnalysisFactory electricalStrengthTestEquipmentImpl = new ElectricalStrengthTestEquipmentImpl();
                    return electricalStrengthTestEquipmentImpl.importExcel(xssfSheet,map,list);
                case TLSYZ:
                    AnalysisFactory thrustTestFingerImpl = new ThrustTestFingerImpl();
                    return thrustTestFingerImpl.importExcel(xssfSheet,map,list);
                case DYXLLNZSYJ:
                    AnalysisFactory powerLineTensionTorsionTesterImpl = new PowerLineTensionTorsionTesterImpl();
                    return powerLineTensionTorsionTesterImpl.importExcel(xssfSheet,map,list);
                case LQCJSYT:
                    AnalysisFactory dropBallImpactTesterImpl = new DropBallImpactTesterImpl();
                    return dropBallImpactTesterImpl.importExcel(xssfSheet,map,list);
                case ZYKC:
                    AnalysisFactory dedicatedCaliperImpl = new DedicatedCaliperImpl();
                    return dedicatedCaliperImpl.importExcel(xssfSheet,map,list);
                case JDDYXWQNZSYJ:
                    AnalysisFactory appliancePowerCordBendingTesterImpl = new AppliancePowerCordBendingTesterImpl();
                    return appliancePowerCordBendingTesterImpl.importExcel(xssfSheet,map,list);
                case SWHDCLZZ:
                    AnalysisFactory visualFieldMeasuringDeviceImpl = new VisualFieldMeasuringDeviceImpl();
                    return visualFieldMeasuringDeviceImpl.importExcel(xssfSheet,map,list);
                case SYCSZZ:
                    visualFieldMeasuringDeviceImpl = new VisualFieldMeasuringDeviceImpl();
                    return visualFieldMeasuringDeviceImpl.importExcel(xssfSheet,map,list);
                case ZDPB:
                    AnalysisFactory vibratingPlateImpl = new VibratingPlateImpl();
                    return vibratingPlateImpl.importExcel(xssfSheet,map,list);
                case BZHP:
                    AnalysisFactory standardPlateImpl = new StandardPlateImpl();
                    return standardPlateImpl.importExcel(xssfSheet,map,list);
                case QXJJ100:
                    AnalysisFactory sphericalFixtureImpl = new SphericalFixtureImpl();
                    return sphericalFixtureImpl.importExcel(xssfSheet,map,list);
                case QXJJ:
                    sphericalFixtureImpl = new SphericalFixtureImpl();
                    return sphericalFixtureImpl.importExcel(xssfSheet,map,list);
                case WFNYJJ:
                    sphericalFixtureImpl = new SphericalFixtureImpl();
                    return sphericalFixtureImpl.importExcel(xssfSheet,map,list);
                case SYTT:
                    AnalysisFactory testProbeImpl = new TestProbeImpl();
                    return testProbeImpl.importExcel(xssfSheet,map,list);
                case ZRRYSYY:
                    AnalysisFactory burningFuelTesterImpl = new BurningFuelTesterImpl();
                    return burningFuelTesterImpl.importExcel(xssfSheet,map,list);
                case ZDRGLG:
                    AnalysisFactory brakeHoseGaugeImpl = new BrakeHoseGaugeImpl();
                    return brakeHoseGaugeImpl.importExcel(xssfSheet,map,list);
                case WBTCWJJD:
                    AnalysisFactory bigExternalProjectionFixtureImpl = new BigExternalProjectionFixtureImpl();
                    return bigExternalProjectionFixtureImpl.importExcel(xssfSheet,map,list);
                case WBTCWJJX:
                    bigExternalProjectionFixtureImpl = new BigExternalProjectionFixtureImpl();
                    return bigExternalProjectionFixtureImpl.importExcel(xssfSheet,map,list);
                case WBTCW:
                    bigExternalProjectionFixtureImpl = new BigExternalProjectionFixtureImpl();
                    return bigExternalProjectionFixtureImpl.importExcel(xssfSheet,map,list);
            }
        } catch (Exception e) {
            throw  new BusinessRuntimeException(e.getMessage());
        }
        return new HashMap();

    }

    public static Map  exportExcel(List<VSourceRecord> sourceList, String sourceType,Map<String, Object> map) throws Exception{
        try {
            switch (sourceType){
                case WD:
                    AnalysisFactory im = new TemperatureImpl();
                    return im.exportExcel(sourceList,map);
                case FDJZS:
                    AnalysisFactory engineSpeedImpl = new EngineSpeedImpl();
                    return engineSpeedImpl.exportExcel(sourceList,map);
                case DYZS:
                    AnalysisFactory dYRotateSpeedImpl = new DYRotateSpeedImpl();
                    return dYRotateSpeedImpl.exportExcel(sourceList,map);
                case DYZQD:
                    AnalysisFactory dYRotateSpeedAccuracyRatingImpl = new DYRotateSpeedAccuracyRatingImpl();
                    return dYRotateSpeedAccuracyRatingImpl.exportExcel(sourceList,map);
                case JL:
                    AnalysisFactory distanceImpl = new DistanceImpl();
                    return distanceImpl.exportExcel(sourceList,map);
                case NJ:
                    AnalysisFactory torqueImpl = new TorqueImpl();
                    return torqueImpl.exportExcel(sourceList,map);
                case FJCSCZLCY:
                    AnalysisFactory roadTestInstrumentImpl = new RoadTestInstrumentImpl();
                    return roadTestInstrumentImpl.exportExcel(sourceList,map);
                case NQPLSYT:
                    AnalysisFactory fatigueUnderFlexingImpl = new FatigueUnderFlexingImpl();
                    return fatigueUnderFlexingImpl.exportExcel(sourceList,map);
                case RYXZJSYT:
                    AnalysisFactory fuelTankCollisionImpl = new FuelTankCollisionImpl();
                    return fuelTankCollisionImpl.exportExcel(sourceList,map);
                case QJY:
                    AnalysisFactory planimeterImpl = new PlanimeterImpl();
                    return planimeterImpl.exportExcel(sourceList,map);
                case FS:
                    AnalysisFactory windSpeedImpl = new WindSpeedImpl();
                    return windSpeedImpl.exportExcel(sourceList,map);
                case HSJZJSYT:
                    AnalysisFactory rearviewMirrorImpact = new RearviewMirrorImpact();
                    return rearviewMirrorImpact.exportExcel(sourceList,map);
                case HSJWQSYT:
                    AnalysisFactory rearviewMirrorBentImpl = new RearviewMirrorBentImpl();
                    return rearviewMirrorBentImpl.exportExcel(sourceList,map);
                case SYQ:
                    AnalysisFactory testTheBallImpl = new TestTheBallImpl();
                    return testTheBallImpl.exportExcel(sourceList,map);
                case WJKZDZWNSYJ:
                    AnalysisFactory microcomputerControlledElectronicUniversalTestingMachineImpl = new MicrocomputerControlledElectronicUniversalTestingMachineImpl();
                    return microcomputerControlledElectronicUniversalTestingMachineImpl.exportExcel(sourceList,map);
                case SYB:
                    AnalysisFactory testBarImpl = new TestBarImpl();
                    return testBarImpl.exportExcel(sourceList,map);
                case SYX:
                    AnalysisFactory testWireImpl = new TestWireImpl();
                    return testWireImpl.exportExcel(sourceList,map);
                case SYZZ:
                    AnalysisFactory testPointsImpl = new TestPointsImpl();
                    return testPointsImpl.exportExcel(sourceList,map);
                case SYZ:
                    AnalysisFactory testFingerImpl = new TestFingerImpl();
                    return testFingerImpl.exportExcel(sourceList,map);
                case SYZHEN:
                    AnalysisFactory testTheNeedleImpl = new TestTheNeedleImpl();
                    return testTheNeedleImpl.exportExcel(sourceList,map);
                case SYXIAO:
                    AnalysisFactory theTestPinImpl = new TheTestPinImpl();
                    return theTestPinImpl.exportExcel(sourceList,map);
                case BZSYZ:
                    AnalysisFactory standardTestIndexImpl = new StandardTestIndexImpl();
                    return standardTestIndexImpl.exportExcel(sourceList,map);
                case DXSYTB:
                    AnalysisFactory testSondeImpl = new TestSondeImpl();
                    return testSondeImpl.exportExcel(sourceList,map);
                case BXSYWZ:
                    AnalysisFactory bendTestRefersImpl = new BendTestRefersImpl();
                    return bendTestRefersImpl.exportExcel(sourceList,map);
                case QYSYZZ:
                    AnalysisFactory ballPressureTestEquipmentImpl = new BallPressureTestEquipmentImpl();
                    return ballPressureTestEquipmentImpl.exportExcel(sourceList,map);
                case ZYSSSY:
                    AnalysisFactory needleFlameWireTesterImpl = new NeedleFlameWireTesterImpl();
                    return needleFlameWireTesterImpl.exportExcel(sourceList,map);
                case ZRSSYY:
                    AnalysisFactory glowWireTesterImpl = new GlowWireTesterImpl();
                    return glowWireTesterImpl.exportExcel(sourceList,map);
                case GFSYX:
                    AnalysisFactory blastChamberImpl = new BlastChamberImpl();
                    return blastChamberImpl.exportExcel(sourceList,map);
                case ZNSZSDTNJY:
                    AnalysisFactory intelligentDigitalLampHeadTorqueMeterImpl = new IntelligentDigitalLampHeadTorqueMeterImpl();
                    return intelligentDigitalLampHeadTorqueMeterImpl.exportExcel(sourceList,map);
                case JDCHWMDSYJ:
                    AnalysisFactory motorVehicleConstantTemperaturePulsationTestingMachineImpl = new MotorVehicleConstantTemperaturePulsationTestingMachineImpl();
                    return motorVehicleConstantTemperaturePulsationTestingMachineImpl.exportExcel(sourceList,map);
                case GTDLSYJ:
                    AnalysisFactory rollerDropTestingMachineImpl = new RollerDropTestingMachineImpl();
                    return rollerDropTestingMachineImpl.exportExcel(sourceList,map);
                case ZDSYLSX:
                    AnalysisFactory brakeTestShowerTankImpl = new BrakeTestShowerTankImpl();
                    return brakeTestShowerTankImpl.exportExcel(sourceList,map);
                case DGZJSYT:
                    AnalysisFactory lightAngleTestBedImpl = new LightAngleTestBedImpl();
                    return lightAngleTestBedImpl.exportExcel(sourceList,map);
                case DHHZSSYJ:
                    AnalysisFactory electricTraceIndexTesterImpl = new ElectricTraceIndexTesterImpl();
                    return electricTraceIndexTesterImpl.exportExcel(sourceList,map);
                case WDXSYT:
                    AnalysisFactory stabilityTestStandImpl = new StabilityTestStandImpl();
                    return stabilityTestStandImpl.exportExcel(sourceList,map);
                case WKQSMSYY:
                    AnalysisFactory thermostatLifeTesterImpl = new ThermostatLifeTesterImpl();
                    return thermostatLifeTesterImpl.exportExcel(sourceList,map);
                case FZLSYJ:
                    AnalysisFactory adhesionTestingMachineImpl = new AdhesionTestingMachineImpl();
                    return adhesionTestingMachineImpl.exportExcel(sourceList,map);
                case LYSYX:
                    AnalysisFactory rainChamberImpl = new RainChamberImpl();
                    return rainChamberImpl.exportExcel(sourceList,map);
                case JSS:
                    AnalysisFactory wireImpl = new WireImpl();
                    return wireImpl.exportExcel(sourceList,map);
                case KDQDSYZZ:
                    AnalysisFactory electricalStrengthTestEquipmentImpl = new ElectricalStrengthTestEquipmentImpl();
                    return electricalStrengthTestEquipmentImpl.exportExcel(sourceList,map);
                case TLSYZ:
                    AnalysisFactory thrustTestFingerImpl = new ThrustTestFingerImpl();
                    return thrustTestFingerImpl.exportExcel(sourceList,map);
                case DYXLLNZSYJ:
                    AnalysisFactory powerLineTensionTorsionTesterImpl = new PowerLineTensionTorsionTesterImpl();
                    return powerLineTensionTorsionTesterImpl.exportExcel(sourceList,map);
                case LQCJSYT:
                    AnalysisFactory dropBallImpactTesterImpl = new DropBallImpactTesterImpl();
                    return dropBallImpactTesterImpl.exportExcel(sourceList,map);
                case ZYKC:
                    AnalysisFactory dedicatedCaliperImpl = new DedicatedCaliperImpl();
                    return dedicatedCaliperImpl.exportExcel(sourceList,map);
                case JDDYXWQNZSYJ:
                    AnalysisFactory appliancePowerCordBendingTesterImpl = new AppliancePowerCordBendingTesterImpl();
                    return appliancePowerCordBendingTesterImpl.exportExcel(sourceList,map);
                case SWHDCLZZ:
                    AnalysisFactory threeDimensionalMeasuringDeviceImpl = new ThreeDimensionalMeasuringDeviceImpl();
                    return threeDimensionalMeasuringDeviceImpl.exportExcel(sourceList,map);
                case SYCSZZ:
                    AnalysisFactory visualFieldMeasuringDeviceImpl = new VisualFieldMeasuringDeviceImpl();
                    return visualFieldMeasuringDeviceImpl.exportExcel(sourceList,map);
                case ZDPB:
                    AnalysisFactory vibratingPlateImpl = new VibratingPlateImpl();
                    return vibratingPlateImpl.exportExcel(sourceList,map);
                case BZHP:
                    AnalysisFactory standardPlateImpl = new StandardPlateImpl();
                    return standardPlateImpl.exportExcel(sourceList,map);
                case QXJJ100:
                    AnalysisFactory sphericalFixtureOneImpl = new SphericalFixtureOneImpl();
                    return sphericalFixtureOneImpl.exportExcel(sourceList,map);
                case QXJJ:
                    AnalysisFactory sphericalFixtureImpl = new SphericalFixtureImpl();
                    return sphericalFixtureImpl.exportExcel(sourceList,map);
                case WFNYJJ:
                    AnalysisFactory externalInternalCircularInspectionToolsImpl = new ExternalInternalCircularInspectionToolsImpl();
                    return externalInternalCircularInspectionToolsImpl.exportExcel(sourceList,map);
                case SYTT:
                    AnalysisFactory testProbeImpl = new TestProbeImpl();
                    return testProbeImpl.exportExcel(sourceList,map);
                case ZRRYSYY:
                    AnalysisFactory burningFuelTesterImpl = new BurningFuelTesterImpl();
                    return burningFuelTesterImpl.exportExcel(sourceList,map);
                case ZDRGLG:
                    AnalysisFactory brakeHoseGaugeImpl = new BrakeHoseGaugeImpl();
                    return brakeHoseGaugeImpl.exportExcel(sourceList,map);
                case WBTCWJJD:
                    AnalysisFactory bigExternalProjectionFixtureImpl = new BigExternalProjectionFixtureImpl();
                    return bigExternalProjectionFixtureImpl.exportExcel(sourceList,map);
                case WBTCWJJX:
                    AnalysisFactory minExternalProjectionFixtureImpl = new MinExternalProjectionFixtureImpl();
                    return minExternalProjectionFixtureImpl.exportExcel(sourceList,map);
                case WBTCW:
                    AnalysisFactory externalProjectionImpl = new ExternalProjectionImpl();
                    return externalProjectionImpl.exportExcel(sourceList,map);
            }
        }catch (Exception e) {
            throw  new BusinessRuntimeException(e.getMessage());
        }

        return new HashMap();
    }

    public static String getValue(XSSFCell xssfRow) {
        if (xssfRow != null) {
//            if (xssfRow != null) {
//                xssfRow.setCellType(xssfRow.CELL_TYPE_STRING);
//            }
            if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
                return String.valueOf(xssfRow.getBooleanCellValue());
            } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
                String result = "";
                if (xssfRow.getCellStyle().getDataFormat() == 22) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    double value = xssfRow.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    result = sdf.format(date);
                } else {
                    double value = xssfRow.getNumericCellValue();
                    CellStyle style = xssfRow.getCellStyle();
                    DecimalFormat format = new DecimalFormat("0.00");
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if (temp.equals("General")) {
                        format.applyPattern("0.00");
                    }
                    result = format.format(value);
                }
                return result;
            } else {
                return xssfRow.getStringCellValue()+"";
            }
        } else {
            return "0";
        }
    }

    /**
     * 获取计量器两列
     * @param xssfSheet
     * @return
     */
    public  static VStandard getStandard2(XSSFSheet xssfSheet){
        VStandard vStandard = new VStandard();
        vStandard.setId(UUIDUtils.generateUuid());
        for (int i = 5; i < 8; i++) {
            try{
                XSSFRow xssfRow = xssfSheet.getRow(i);
                XSSFCell value = xssfRow.getCell(2);//第一次循环取到为名称  第二次循环取到为型号 第三次为编 号
                XSSFCell value1 = xssfRow.getCell(5);//第一次循环取到为证书编号  第二次循环取到有效期  第三次为准确度最大允差
                if(i==5){
                    vStandard.setName(ImportExcelUtils.getValue(value));
                    vStandard.setCertificateNumber(ImportExcelUtils.getValue(value1));
                }else if(i == 6){
                    vStandard.setModelNumber(ImportExcelUtils.getValue(value));
                    vStandard.setValidity(ImportExcelUtils.getValue(value1));
                }else{
                    vStandard.setCode(ImportExcelUtils.getValue(value));
                    vStandard.setAccuracy(ImportExcelUtils.getValue(value1));
                }
            }catch (Exception e) {
                throw  new BusinessRuntimeException(e.getMessage());
            }

        }
        return vStandard;
    }

    /**
     * 三列
     * @param xssfSheet
     * @return
     */
    public  static VStandard getStandard3(XSSFSheet xssfSheet){
        VStandard vStandard = new VStandard();
        vStandard.setId(UUIDUtils.generateUuid());
        for (int i = 4; i < 6; i++) {
            try{
                XSSFRow xssfRow = xssfSheet.getRow(i);
                XSSFCell value = xssfRow.getCell(2);//第一次循环取到为名称  第二次循环取到为准确度
                XSSFCell value1 = xssfRow.getCell(6);//第一次循环取到为型号  第二次循环取到编号
                XSSFCell value2 = xssfRow.getCell(10);//第一次循环取到为证书编号  第二次循环取到有效期
                if(i==4){
                    vStandard.setName(ImportExcelUtils.getValue(value));
                    vStandard.setModelNumber(ImportExcelUtils.getValue(value1));
                    vStandard.setCertificateNumber(ImportExcelUtils.getValue(value2));
                }else{
                    vStandard.setAccuracy(ImportExcelUtils.getValue(value));
                    vStandard.setCode(ImportExcelUtils.getValue(value1));
                    vStandard.setValidity(ImportExcelUtils.getValue(value2));
                }
            }catch (Exception e) {
                throw  new BusinessRuntimeException(e.getMessage());
            }
        }
        return vStandard;
    }
}
