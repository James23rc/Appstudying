package com.example.gank.bean;

import java.util.List;

/**
 * Created by Super_chao on 2018/6/9.
 */

public class NewsJsonBean {
    public Results results;
    public static class Results {
        public List<iOS> iOS;
        public static class iOS{
            private String desc;
            private String publishedAt;
            private String type;
            private String url;
            private String who;

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getDesc() {
                return desc;
            }


            public String getPublishedAt() {
                return publishedAt;
            }

            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getWho() {
                return who;
            }

            public void setWho(String who) {
                this.who = who;
            }
        }

        public List<android> Android;
        public static class android{
            private String desc;
            private String publishedAt;
            private String type;
            private String url;
            private String who;
            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getDesc() {
                return desc;
            }

            public String getPublishedAt() {
                return publishedAt;
            }

            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getWho() {
                return who;
            }

            public void setWho(String who) {
                this.who = who;
            }

        }

        public List<reconmend> ?????????;
        public static class reconmend{
            private String desc;
            private String publishedAt;
            private String type;
            private String url;
            private String who;

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getDesc() {
                return desc;
            }

            public String getPublishedAt() {
                return publishedAt;
            }

            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getWho() {
                return who;
            }

            public void setWho(String who) {
                this.who = who;
            }
        }

        public List<source> ????????????;
        public static  class source{private String desc;
            private String publishedAt;
            private String type;
            private String url;
            private String who;

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getDesc() {
                return desc;
            }

            public String getPublishedAt() {
                return publishedAt;
            }

            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getWho() {
                return who;
            }

            public void setWho(String who) {
                this.who = who;
            }

        }

        public List<weifare> ??????;
        public static class weifare{
            private String desc;
            private String publishedAt;
            private String type;
            private String url;
            private String who;

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getDesc() {
                return desc;
            }

            public String getPublishedAt() {
                return publishedAt;
            }

            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getWho() {
                return who;
            }

            public void setWho(String who) {
                this.who = who;
            }
        }

        public List<video> ????????????;
        public static class video{
            private String desc;
            private String publishedAt;
            private String type;
            private String url;
            private String who;

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getDesc() {
                return desc;
            }

            public String getPublishedAt() {
                return publishedAt;
            }

            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getWho() {
                return who;
            }

            public void setWho(String who) {
                this.who = who;
            }

        }

        public List<source> getSources() {
            return ????????????;
        }

        public List<video> get????????????() {
            return ????????????;
        }

        public List<weifare> get??????() {
            return ??????;
        }

        public void set????????????(List<video> ????????????) {
            this.???????????? = ????????????;
        }

        public void setSources(List<source> sources) {
            this.???????????? = sources;
        }

        public void set??????(List<weifare> ??????) {
            this.?????? = ??????;
        }

        public List<reconmend> get?????????() {
            return ?????????;
        }

        public void set?????????(List<reconmend> recommend) {
            this.????????? = recommend;
        }

        public List<android> getAndroid() {
            return Android;
        }

        public void setiOS(List<Results.iOS> iOS) {
            this.iOS = iOS;
        }

        public List<Results.iOS> getiOS() {
            return iOS;
        }

        public void setAndroid(List<android> android) {
            Android = android;
        }
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }
}

