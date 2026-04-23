# Item CF - 基于物品的协同过滤推荐算法

基于 Spark ML 实现的协同过滤推荐系统，包含完整课程设计报告。

## 项目简介

本项目实现了基于物品的协同过滤算法，用于推荐系统：
- Item-based Collaborative Filtering
- 用户行为数据分析
- 推荐结果评估

## 技术栈

- **语言**: Scala
- **框架**: Spark MLlib
- **算法**: ALS (Alternating Least Squares)

## 项目结构

```
item_CF/
├── src/              # 源代码
├── data/             # 数据集
├── report/           # 课程设计报告
└── README.md
```

## 功能特性

- ✅ 协同过滤算法实现
- ✅ 用户-物品矩阵构建
- ✅ 相似度计算
- ✅ 推荐结果生成
- ✅ 模型评估指标

## 快速开始

### 环境要求

- Scala 2.11+
- Spark 2.4+
- SBT

### 运行

```bash
sbt run
```

## 算法说明

协同过滤核心思想：
1. 构建用户-物品评分矩阵
2. 计算物品相似度
3. 根据用户历史行为推荐相似物品

## 评估指标

| 指标 | 说明 |
|------|------|
| RMSE | 均方根误差 |
| MAE | 平均绝对误差 |
| Precision | 准确率 |
| Recall | 召回率 |

## License

MIT License
