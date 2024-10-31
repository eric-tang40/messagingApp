from rest_framework import serializers
# Import all models that need to be serialized here
from .models import MyUser

class MyUserSerializer(serializers.ModelSerializer):
    friends = serializers.DictField(  # allows friends to be a dictionary (aka DictField)
        child=serializers.ListField(
            child=serializers.CharField()
        ),
        required=False,  # Allows friend_groups to be optional
        default = dict
    )

    class Meta:
        model = MyUser
        fields = ['id', 'username', 'password', 'email', 'bio', 'friends']
        extra_kwargs = {'password': {'write_only': True}}

    def create(self, validated_data):
        friends_list = validated_data.pop('friends', {}) # Extract friends, since we need to initialize arrays differently

        user = MyUser(
            username=validated_data['username'],
            email=validated_data['email'],
            bio=validated_data.get('bio', "")
        )
        user.set_password(validated_data['password'])

        # initialize arrays here
        # If 'friends' is provided and is a dict, set it
        user.friends = friends_list
        user.save()
        return user


""" This is an example of what "serialized" data looks like
{
    "id": 4,
    "username": "Ringo",
    "password": "test4",
    "email": "ringo@example.com",
    "bio": "I'm ringo"
}
"""

