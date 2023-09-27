export interface Status{
    statusCode: number,
    message: string
}

export interface SignupRequest {
    name: string
    email: string
    password: string
    confirmPassword: string
    role: string
}

export interface LoginRequest {
    email: string
    password: string
}

export interface LoginResp extends Status{
    token: string
    // refreshToken: string
    // expiration: string
    // name: string
    // email: string
}

export interface Account {
    id: string
    email: string
    name: string
    password: string
    role: string
}

export interface Interest {
    id: string
    name: string
    email: string
    phone: string
    country: string
    gender: string
    qualification: string
}