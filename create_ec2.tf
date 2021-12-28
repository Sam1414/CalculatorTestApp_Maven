terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.0"
    }
  }
}

provider "aws" {
  region = "us-east-2"
}

resource "aws_security_group" "sam_tf_SG" {
  name          = "sam_tf_SG"
  description   = "Security Group for DevOps Terraform Practice"

  ingress       = [
    {
      description       = "allow ssh port"
      from_port         = 22
      to_port           = 22
      protocol          = "tcp"
      cidr_blocks       = ["0.0.0.0/0"]
      ipv6_cidr_blocks  = ["::/0"]
      self              = null
      security_groups   = null
      prefix_list_ids   = null
    }, 
    {
      description	= "allowing all tcp"
      from_port         = 0
      to_port           = 65535
      protocol          = "tcp"
      cidr_blocks       = ["0.0.0.0/0"]
      ipv6_cidr_blocks  = ["::/0"]
      self              = null
      security_groups   = null
      prefix_list_ids   = null
    }
  ]

  egress         = [
    {
      description       = "outbound rules"
      from_port         = 0
      to_port           = 0
      protocol          = "-1"
      cidr_blocks       = ["0.0.0.0/0"]
      ipv6_cidr_blocks  = ["::/0"]
      self              = null
      security_groups   = null
      prefix_list_ids   = null
    }
  ]
}

resource "aws_instance" "sam-tf" {
  ami                   = "ami-0443305dabd4be2bc"
  instance_type         = "t2.micro"
  security_groups       = ["sam_tf_SG"]
  key_name              = "sam_tf_key"

  tags                  = {
    Name        = "sam-tf-instance"
    Owner       = "samarth.agarwal@nagarro.com"
    Environment = "dev"
    Project     = "FT"
  }
}
