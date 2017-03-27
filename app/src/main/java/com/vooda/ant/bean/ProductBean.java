package com.vooda.ant.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Create by  leijiaxq
 * Date       2017/3/15 13:34
 * Describe   商品bean
 */
public class ProductBean extends BaseBean {
    /**
     * result : ok
     * message : 获取成功.
     * data : [{"row_id":11,"ProID":1986,"ProductName":"番薯叶","Price":5.5,"FirstImage":"/upload/2016/10/18/2164a860-d204-4ad0-b554-732ee1717bf7.jpg","ImageUrl":"/upload/2016/10/18/2164a860-d204-4ad0-b554-732ee1717bf7.jpg,/upload/2016/10/18/2e3597ff-0afa-4949-8ce2-ac04427bd77b.jpg","Description":"红薯叶中有丰富的黏液蛋白，它具有提高人体免疫力，增强免疫功能、促进新陈代谢的作用。常食红薯叶可延缓衰老","StockCount":998,"EditTime":"2016-11-27 14:43:02","AddTime":"2016-10-18 15:55:48","IsStatus":1,"CID":226,"ClassName":"蔬菜","ParentID":"209226","Type":1,"BuyCount":7,"CommentCount":0,"UnitName":"斤（时价）"},{"row_id":12,"ProID":1987,"ProductName":"葱","Price":1.5,"FirstImage":"/upload/2016/10/18/8a293d96-c597-421e-8cf9-8bf3aaccaaf0.jpg","ImageUrl":"/upload/2016/10/18/8a293d96-c597-421e-8cf9-8bf3aaccaaf0.jpg,/upload/2016/10/18/803dae7a-2fea-4f2d-8328-2145164792be.jpg","Description":"煮汤，可治伤风寒的寒热，消除中风后面部和眼睛浮肿。药性入手太阴肺经，能发汗；又入足阳阴胃经，可治伤寒骨肉疼痛，咽喉麻痹肿痛不通，并可安胎。使用于眼睛，可眼清目明、轻身，使肌肤润泽，精力充沛，抗衰老，祛除肝脏中的邪气，通利中焦，调五脏，解各种药物的药毒，通大小肠，治疗腹泻引起的抽筋以及奔豚气、脚气，心腹绞痛，眼睛发花，心烦闷。另可通关节，止鼻孔流血，利大小便。治腹泻不止和便中带血。能达解表和里，除去风湿。","StockCount":95,"EditTime":"2016-11-08 16:44:23","AddTime":"2016-10-18 15:57:35","IsStatus":1,"CID":228,"ClassName":"调味菜","ParentID":"209228","Type":1,"BuyCount":36,"CommentCount":0,"UnitName":"两（时价）"},{"row_id":13,"ProID":1988,"ProductName":"草菇","Price":20,"FirstImage":"/upload/2016/10/18/1c0800b0-56d3-4f73-8adb-55d2942cbe8d.jpg","ImageUrl":"/upload/2016/10/18/1c0800b0-56d3-4f73-8adb-55d2942cbe8d.jpg,/upload/2016/10/18/7854de75-0bb4-41c1-a667-a69eb99acc9f.jpg","Description":"【主治】暑热烦渴；体质虚弱；头晕乏力；高血压","StockCount":998,"EditTime":"2016-11-27 14:43:27","AddTime":"2016-10-18 15:58:35","IsStatus":1,"CID":226,"ClassName":"蔬菜","ParentID":"209226","Type":1,"BuyCount":2,"CommentCount":0,"UnitName":"斤（时价）"},{"row_id":14,"ProID":1989,"ProductName":"豆苗","Price":10,"FirstImage":"/upload/2016/10/18/45ce1a76-41b0-45a9-9466-8113c83d41e0.jpg","ImageUrl":"/upload/2016/10/18/45ce1a76-41b0-45a9-9466-8113c83d41e0.jpg,/upload/2016/10/18/ce8c7040-d0c7-4382-9b70-73a03b2eeb2f.jpg","Description":"豆苗的频色很鲜明，质地又很柔软，含有极丰富的钙质、B族维生素、维生素C和胡萝卜素，有利尿、止泻、消肿、止痛和助消化等作用。豌豆苗能治疗晒黑的肌肤，使肌肤清爽不油腻。在乡间，女子没有什么化妆品，经常在烈日之下工作，极易使肌肤黝黑，民间用豆苗打汁，涂擦肌肤，可使面部油脂消除，保持肌肤幼嫩。除具有清热的功用外，，豆苗也可使肌肤光滑柔软。豌豆又名澡豆，可令人面色光泽。","StockCount":999,"EditTime":"2016-11-27 14:44:48","AddTime":"2016-10-18 15:59:44","IsStatus":1,"CID":226,"ClassName":"蔬菜","ParentID":"209226","Type":1,"BuyCount":2,"CommentCount":0,"UnitName":"斤（时价）"},{"row_id":15,"ProID":1990,"ProductName":"皇帝菜/斤","Price":0.01,"FirstImage":"/upload/2016/10/18/69f4d5a6-5849-474f-941d-5f7971e90f99.jpg","ImageUrl":"/upload/2016/10/18/69f4d5a6-5849-474f-941d-5f7971e90f99.jpg,/upload/2016/10/18/90cea49b-6589-4e60-a7db-231a614e5261.jpg","Description":"皇帝菜的茎和叶可以同食，清气甘香，鲜香嫩脆，一般的营养成份无所不备","StockCount":0,"EditTime":"2016-11-27 14:09:46","AddTime":"2016-10-18 16:07:21","IsStatus":1,"CID":226,"ClassName":"蔬菜","ParentID":"209226","Type":1,"BuyCount":0,"CommentCount":0,"UnitName":"斤(无货)"},{"row_id":16,"ProID":1991,"ProductName":"尖椒","Price":1.3,"FirstImage":"/upload/2016/10/18/4f92d3a2-9ff0-4424-abf7-7a3ccbb1786d.jpg","ImageUrl":"/upload/2016/10/18/4f92d3a2-9ff0-4424-abf7-7a3ccbb1786d.jpg,/upload/2016/10/18/12c818d4-f2c7-4187-9a89-b5fb4fdee58d.jpg","Description":"辣椒含有丰富的维生素 C ，可以控制心脏病及冠[1]  状动脉硬化，降低胆固醇；含有较多抗氧化物质，可预防癌 油焖尖椒 油焖尖椒(20张)  症及其他慢性疾病；可以使呼吸道畅通，用以治疗咳嗽、感冒。吃饭不香、饭量减少时，在菜里放上一些辣椒就能改善食欲，增加饭量。","StockCount":998,"EditTime":"2016-11-06 17:48:15","AddTime":"2016-10-18 16:09:05","IsStatus":1,"CID":228,"ClassName":"调味菜","ParentID":"209228","Type":1,"BuyCount":6,"CommentCount":0,"UnitName":"两（时价）"},{"row_id":17,"ProID":1992,"ProductName":"油麦菜（剪刀菜）","Price":7,"FirstImage":"/upload/2016/10/18/bbef2d20-b086-4bd4-887c-392116b518a2.jpg","ImageUrl":"/upload/2016/10/18/bbef2d20-b086-4bd4-887c-392116b518a2.jpg,/upload/2016/10/18/3a689bea-20c9-460f-80c6-eadf3a730da8.jpg","Description":"油麦菜：油麦菜含有大量维生素和大量钙、铁、蛋白质、脂肪、维生素A、VB1、VB2等营养成分，是生食蔬菜中的上品，有\u201c凤尾\u201d之称。油麦菜具有降低胆固醇、治疗神经衰弱、清燥润肺、化痰止咳等功效，是一种低热量、高营养的蔬菜。","StockCount":998,"EditTime":"2016-11-27 14:40:29","AddTime":"2016-10-18 16:11:31","IsStatus":1,"CID":226,"ClassName":"蔬菜","ParentID":"209226","Type":1,"BuyCount":8,"CommentCount":0,"UnitName":"斤（时价）"},{"row_id":18,"ProID":1993,"ProductName":"芥蓝菜","Price":7,"FirstImage":"/upload/2016/10/18/1c058761-0bab-4fa4-8923-1650ac62a0d9.jpg","ImageUrl":"/upload/2016/10/18/1c058761-0bab-4fa4-8923-1650ac62a0d9.jpg,/upload/2016/10/18/2eeb8d21-e660-4750-8137-290f3e4ee01f.jpg","Description":"芥蓝中另一种独特的苦味成分是金鸡纳霜，能抑制过度兴奋的体温中枢，起到消暑解热作用。它还含有大量膳食纤维，能防止便秘。降低胆固醇，软化血管，预防心脏病等功效。有利水化痰、解毒祛风、消暑解热、解劳乏、清心明目等功效，能润肠祛热气、下虚火、止牙龈出血，对肠胃热重、熬夜失眠、虚火上升、牙龈肿胀出血等也有辅助治疗效果。","StockCount":996,"EditTime":"2016-11-27 14:39:29","AddTime":"2016-10-18 16:12:54","IsStatus":1,"CID":226,"ClassName":"蔬菜","ParentID":"209226","Type":1,"BuyCount":15,"CommentCount":0,"UnitName":"斤（时价）"},{"row_id":19,"ProID":1994,"ProductName":"金针菇","Price":8,"FirstImage":"/upload/2016/10/18/ba77e061-6222-40c6-b4c2-6fc99f6f0e54.jpg","ImageUrl":"/upload/2016/10/18/ba77e061-6222-40c6-b4c2-6fc99f6f0e54.jpg,/upload/2016/10/18/8a365936-64cd-40ef-85ec-168b176a7293.jpg","Description":"鲜金针菇富含B族维生素、维生素C、碳水化合物、矿物质、胡萝卜素、多种氨基酸、植物血凝素、多糖、牛磺酸、香菇嘌呤、麦冬甾醇、细胞溶解毒素、冬菇细胞毒素等","StockCount":994,"EditTime":"2016-11-27 14:42:47","AddTime":"2016-10-18 16:14:03","IsStatus":1,"CID":226,"ClassName":"蔬菜","ParentID":"209226","Type":1,"BuyCount":18,"CommentCount":0,"UnitName":"斤（时价）"},{"row_id":20,"ProID":1996,"ProductName":"韭菜花","Price":12,"FirstImage":"/upload/2016/10/18/62a1d1d1-9d5d-415d-a8a3-7eda35b71cba.jpg","ImageUrl":"/upload/2016/10/18/62a1d1d1-9d5d-415d-a8a3-7eda35b71cba.jpg,/upload/2016/10/18/3aefed04-bf71-4a06-b59d-4b7f70e42ed9.jpg","Description":"韭花食之能生津开胃，增强食欲，促进消化。 韭花富含钙、磷、铁、胡萝卜素、核黄素、抗坏血酸等有益健康的成分。","StockCount":995,"EditTime":"2016-11-27 14:42:42","AddTime":"2016-10-18 16:17:39","IsStatus":1,"CID":226,"ClassName":"蔬菜","ParentID":"209226","Type":1,"BuyCount":12,"CommentCount":0,"UnitName":"斤（时价）"}]
     * dataSet : null
     * other : null
     */

    public String result;
    public String           message;
    public Object           dataSet;
    public Object           other;
    public List<DataEntity> data;

    public static class DataEntity implements Parcelable {
        /**
         * row_id : 11
         * ProID : 1986
         * ProductName : 番薯叶
         * Price : 5.5
         * FirstImage : /upload/2016/10/18/2164a860-d204-4ad0-b554-732ee1717bf7.jpg
         * ImageUrl : /upload/2016/10/18/2164a860-d204-4ad0-b554-732ee1717bf7.jpg,/upload/2016/10/18/2e3597ff-0afa-4949-8ce2-ac04427bd77b.jpg
         * Description : 红薯叶中有丰富的黏液蛋白，它具有提高人体免疫力，增强免疫功能、促进新陈代谢的作用。常食红薯叶可延缓衰老
         * StockCount : 998
         * EditTime : 2016-11-27 14:43:02
         * AddTime : 2016-10-18 15:55:48
         * IsStatus : 1
         * CID : 226
         * ClassName : 蔬菜
         * ParentID : 209226
         * Type : 1
         * BuyCount : 7
         * CommentCount : 0
         * UnitName : 斤（时价）
         */

        public int row_id;
        public int    ProID;
        public String ProductName;
        public double Price;
        public String FirstImage;
        public String ImageUrl;
        public String Description;
        public int    StockCount;
        public String EditTime;
        public String AddTime;
        public int    IsStatus;
        public int    CID;
        public String ClassName;
        public String ParentID;
        public int    Type;
        public int    BuyCount;
        public int    CommentCount;
        public String UnitName;


        public int mGood;
        public int mMiddle;
        public int mBad;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.row_id);
            dest.writeInt(this.ProID);
            dest.writeString(this.ProductName);
            dest.writeDouble(this.Price);
            dest.writeString(this.FirstImage);
            dest.writeString(this.ImageUrl);
            dest.writeString(this.Description);
            dest.writeInt(this.StockCount);
            dest.writeString(this.EditTime);
            dest.writeString(this.AddTime);
            dest.writeInt(this.IsStatus);
            dest.writeInt(this.CID);
            dest.writeString(this.ClassName);
            dest.writeString(this.ParentID);
            dest.writeInt(this.Type);
            dest.writeInt(this.BuyCount);
            dest.writeInt(this.CommentCount);
            dest.writeString(this.UnitName);
            dest.writeInt(this.mGood);
            dest.writeInt(this.mMiddle);
            dest.writeInt(this.mBad);
        }

        public DataEntity() {
        }

        protected DataEntity(Parcel in) {
            this.row_id = in.readInt();
            this.ProID = in.readInt();
            this.ProductName = in.readString();
            this.Price = in.readDouble();
            this.FirstImage = in.readString();
            this.ImageUrl = in.readString();
            this.Description = in.readString();
            this.StockCount = in.readInt();
            this.EditTime = in.readString();
            this.AddTime = in.readString();
            this.IsStatus = in.readInt();
            this.CID = in.readInt();
            this.ClassName = in.readString();
            this.ParentID = in.readString();
            this.Type = in.readInt();
            this.BuyCount = in.readInt();
            this.CommentCount = in.readInt();
            this.UnitName = in.readString();
            this.mGood = in.readInt();
            this.mMiddle = in.readInt();
            this.mBad = in.readInt();
        }

        public static final Parcelable.Creator<DataEntity> CREATOR = new Parcelable.Creator<DataEntity>() {
            @Override
            public DataEntity createFromParcel(Parcel source) {
                return new DataEntity(source);
            }

            @Override
            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };
    }

}
