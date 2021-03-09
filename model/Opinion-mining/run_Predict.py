'''
预测全流程文件
使用命令行：
python run_Predict.py rebuild IsDebug
参数：  
    rebuild 强制生成数据，默认0
    IsDebug 调试模式，每步都等确认；默认0

快速预测：python run_Predict.py
重生成数据并预测：python run_Predict.py 1
调试模式预测：python run_Predict.py 1 1
'''

import os
import sys
import argparse

parser = argparse.ArgumentParser(description='数据预处理，包含训练数据与预测数据的预处理。')
parser.add_argument('-predict', type=int, default="0",
                    help='预测数据选择，0=测试数据，1=训练数据； 默认0')
parser.add_argument('-product', type=str, default="泰奇",
                    help='选择预测产品，默认泰奇')
parser.add_argument('-rebuild', type=int, default="0",
                    help='强制重生成所有训练数据，默认0')
parser.add_argument('-debug', type=int, default="0",
                    help='调试模式，每步都等确认；默认0')
args = parser.parse_args()

rebuild = args.rebuild
IsDebug = args.debug
predict = args.predict
product = args.product

# 控制预测哪个文件
lstP = ['./TEST/{}.csv'.format(product), './TRAIN/Train_reviews.csv']

# 复制数据入口： /data/test_reviews.csv
strCmd = 'cp %s ./data/test_reviews.csv' % lstP[predict]
ret = os.system(strCmd)

'''
#2019/8/30 todo: 优化步骤，把任务写成任务包，提取每步的说明与命令自动执行
思路：把每个步骤统一成一个执行方法, 参数以字典方式传入
dicCmd = {
    'begin':'', #开始的提示
    'cmd':[],   #命令行，
    'end':'',   #结束的提示
    'predict':1,#只有预测模型才执行
    }
'''

# 任务包清单
lstCommands = [
    {
        'begin': 'STEP 1: 原始数据文件，生成序列标注数据文件...',  # 开始的提示
        'cmd': [
            'python pre_Proecess_zlr.py -model 0 -rebuild %d ' % rebuild,
        ],  # 命令行，
        'end': '',  # 结束的提示
        'predict': 0

    },
    {
        'begin': 'STEP 2: 调用标注模型进行标注预测...',  # 开始的提示
        'cmd': [
            'python BERT_NER.py --do_predict=true',
        ],  # 命令行，
        'end': 'STEP 2: 标注模型预测完成。',  # 结束的提示
    },
    {
        'begin': 'STEP 3: 调用标注结果处理程序，把提取结果格式化...',  # 开始的提示
        'cmd': [
            'python labelpick.py',
        ],  # 命令行，
        'end': '',  # 结束的提示
    },
    {
        'begin': 'STEP 4: 把提取结果复制到属性模型和观点模型目录中...',  # 开始的提示
        'cmd': [
            'cp ./output/picklabel_test.txt ./Category/data/test.tsv',
            # 'cp ./output/picklabel_test.txt ./Polarity/data/test.tsv',
            'python pre_Proecess_zlr.py -model 0',
        ],  # 命令行，
        'end': '',  # 结束的提示
    },
    {
        'begin': 'STEP 5: 调用属性模型和观点模型进行预测...',  # 开始的提示
        'cmd': [
            './m_predict.sh',
        ],  # 命令行，
        'end': 'STEP 5: 属性分类和观点分类预测完成。',  # 结束的提示
    },
    {
        'begin': 'STEP 6: 正在合并最后结果...',  # 开始的提示
        'cmd': [
            'sudo python3 MergeResult.py',
        ],  # 命令行，
        'end': '',  # 结束的提示
    },
    {
        'begin': 'STEP 7: 正在评估模型...',  # 开始的提示
        'cmd': [
            'python3 modelscore.py',
        ],  # 命令行，
        'end': '',  # 结束的提示
        'predict': 1
    },
    {
        'begin': 'STEP 8: 生成json文件...',  # 开始的提示
        'cmd': [
            'python3 final_process.py -product=%s' % product,
        ],  # 命令行，
        'end': '',  # 结束的提示
    }
]


##执行任务包
def RunCmds(lstCmds, IsDebug=0, predict=0):
    pass
    print('开始自动处理...')
    for x in lstCmds:
        if 'predict' in x.keys():
            if x['predict'] != predict: continue

        if x['begin']:
            print(x['begin'])

        for strCmd in x['cmd']:
            os.system(strCmd)

        if x['end']:
            print(x['end'])

        if IsDebug:
            bk = input("按回车键继续...")


# 调用执行任务包
RunCmds(lstCommands, IsDebug=IsDebug, predict=predict)

sys.exit(0)

if __name__ == '__main__':
    pass
