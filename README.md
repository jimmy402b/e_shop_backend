# E-Shop Backend

一个简单的 Java 电商后端服务，用于演示 DevOps Platform CLI 的 `scan` 命令。

## 项目结构

```
e-shop-backend/
├── pom.xml
├── src/main/java/com/eshop/
│   ├── EShopApplication.java
│   ├── controller/    (4 files)
│   ├── service/       (5 files)
│   ├── dao/           (4 files)
│   ├── model/         (4 files)
│   └── util/          (3 files)
└── src/test/java/com/eshop/
    ├── service/       (3 files)
    ├── dao/           (1 file)
    └── util/          (2 files)
```

## 构建

```bash
mvn compile -B
mvn test -B
```

## 许可

MIT
