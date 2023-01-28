import math

TargetPos = [300, 300]
CurrentPos = [80, -100]
SLOWDIST = 20
dist_from_end = math.sqrt(((TargetPos[0] - CurrentPos[0]) ^ 2) + ((TargetPos[1] - CurrentPos[1]) ^ 2))
speed = 0


def get_dist():
    return abs(math.sqrt(((TargetPos[0] - CurrentPos[0]) * (TargetPos[0] - CurrentPos[0])) + ((TargetPos[1] - CurrentPos[1]) * (TargetPos[1] - CurrentPos[1]))))


def get_speed(val):
    _slowDist = max([val, SLOWDIST])
    return (val - 0) * (1 - 0) / (_slowDist - 0) + 0


def get_out(_current_x, _current_y, _target_x, _target_y):
    y_max = (_target_y - _current_y)
    x_max = _target_x - _current_x
    maxList = [abs(x_max), abs(y_max)]
    denominator = max(maxList)
    CurrentPos[0] += x_max / denominator
    CurrentPos[1] += y_max / denominator
    return [(y_max / denominator), (x_max / denominator)]


def get_motor_power(_x_joy, _y_joy, _rx):
    _y_joy *= -1
    maxList = [(abs(_x_joy) + abs(_y_joy) + abs(_rx)), 1]
    denominator = max(maxList)

    frontLeftPower = (((_y_joy + _x_joy) + _rx) / denominator) * get_speed(get_dist())
    backLeftPower = (((_y_joy - _x_joy) + _rx) / denominator) * get_speed(get_dist())
    frontRightPower = (((_y_joy - _x_joy) - _rx) / denominator) * get_speed(get_dist())
    backRightPower = (((_y_joy + _x_joy) - _rx) / denominator) * get_speed(get_dist())

    print("Front Right Motor: ", frontRightPower)
    print("Front Left Motor: ", frontLeftPower)
    print("Back Right Motor: ", backRightPower)
    print("Back Left Motor: ", backLeftPower)
    print("")


print(get_out(CurrentPos[0], CurrentPos[1], TargetPos[0], TargetPos[1]))
get_motor_power(get_out(CurrentPos[0], CurrentPos[1], TargetPos[0], TargetPos[1])[0], get_out(CurrentPos[0], CurrentPos[1], TargetPos[0], TargetPos[1])[1], 0)

while (TargetPos[0] != CurrentPos[0] or TargetPos[1] != CurrentPos[1]):
    print(get_out(CurrentPos[0], CurrentPos[1], TargetPos[0], TargetPos[1]))
    get_motor_power(get_out(CurrentPos[0], CurrentPos[1], TargetPos[0], TargetPos[1])[0],
                    get_out(CurrentPos[0], CurrentPos[1], TargetPos[0], TargetPos[1])[1], 0)
    print("Position: ", CurrentPos)
    print("")

